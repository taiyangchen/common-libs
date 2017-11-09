package com.sm.common.libs.pool;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

import com.sm.common.libs.core.LoggerSupport;
import com.sm.common.libs.core.SimpleThreadFactory;
import com.sm.common.libs.util.CollectionUtil;
import com.sm.common.libs.util.ExecutorUtil;

/**
 * 延迟执行的缓冲区
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月16日 下午5:32:25
 * @param <T>
 */
public class DelayExecuteBuffer<T> extends LoggerSupport implements Buffer<T> {

  /**
   * 名称
   */
  private String name;

  /**
   * 批处理执行器@see IBatchExecutor
   */
  private BatchExecutor<T> batchExecutor;

  /**
   * 记录池的原子引用 @see RecordPool
   */
  private AtomicReference<RecordPool<T>> recordPoolReference = new AtomicReference<RecordPool<T>>();

  /**
   * 延时调度器
   */
  private ScheduledExecutorService scheduler = Executors
      .newSingleThreadScheduledExecutor(new SimpleThreadFactory(DelayExecuteBuffer.class.getSimpleName() + "-single"));

  /**
   * 间隔时间，单位毫秒
   */
  private long checkInterval = 1000;

  /**
   * 缓存池大小
   */
  private int poolSize = 1024;

  /**
   * 批次处理记录数
   */
  private int batchSize = 10;

  /**
   * 处理线程数
   */
  private int threads = 1;

  /**
   * 是否激活
   */
  private boolean active;

  /**
   * 异常监听器 @see ExceptionListener
   */
  private ExceptionListener<T> exceptionListener;

  /**
   * 可重入锁
   */
  private final Lock lock = new ReentrantLock();

  /**
   * 当前线程
   */
  private final Thread current = Thread.currentThread();

  public DelayExecuteBuffer() {
    this.name = this.getClass().getSimpleName();
  }

  public DelayExecuteBuffer(String name) {
    this.name = name;
  }

  @Override
  public boolean add(T record) {
    lock.lock();
    try {
      while (full()) {
        try {
          logger.warn("record pool [{}] is full,park [{}] ", name, current);
          LockSupport.park();
        } catch (Exception e) {
          logger.error("park error", e);
          LockSupport.unpark(current);
          // FIXME
          Thread.currentThread().interrupt();
        }
      }

      boolean ret = getRecordPool().add(record);
      logger.trace("add record to pool [{}]. poolSize=[{}], remainCapacity=[{}], record=[{}], ret=[{}]",
          new Object[] {name, poolSize, getRecordPool().remainCapacity(), record, ret});
      return ret;
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean add(T record, long timeout, TimeUnit unit) {
    lock.lock();
    try {
      while (full()) {
        try {
          long nanos = unit.toNanos(timeout);
          logger.warn("record pool [{}] is full,park [{}] [{}] nanos", name, current, nanos);
          LockSupport.parkNanos(nanos);
        } catch (Exception e) {
          logger.error("park error", e);
          LockSupport.unpark(current);
          // FIXME
          Thread.currentThread().interrupt();
        }
      }

      boolean ret = getRecordPool().add(record);
      logger.trace("add record to pool [{}]. poolSize=[{}], remainCapacity=[{}], record=[{}], ret=[{}]",
          new Object[] {name, poolSize, getRecordPool().remainCapacity(), record, ret});
      return ret;
    } finally {
      lock.unlock();
    }
  }

  /**
   * 处理记录池中的所有记录
   */
  public void flush() {
    lock.lock();
    try {
      List<T> records = getRecordPool().getWholeRecords();
      execute(records);
    } finally {
      lock.unlock();
    }

  }

  @Override
  public boolean isActive() {
    return active;
  }

  @Override
  public void start() {
    this.recordPoolReference.set(new DefaultRecordPool<T>(poolSize));
    this.recordPoolReference.get().setBatchSize(batchSize);

    for (int i = 0; i < threads; i++) {
      scheduler.scheduleWithFixedDelay(new Runnable() {
        @Override
        public void run() {

          logger.trace("schedule: pop from record pool [{}]. poolSize=[{}], remainCapacity=[{}]",
              new Object[] {name, poolSize, getRecordPool().remainCapacity()});

          final List<T> records = getRecordPool().asList();

          execute(records);

        }
      }, checkInterval, checkInterval, TimeUnit.MILLISECONDS);
    }

    active = true;
  }

  @Override
  public void stop() {
    active = false;
    flush();
    ExecutorUtil.shutdownAndAwait(scheduler);
  }

  /**
   * 处理批量记录
   * 
   * @param records 批量记录
   */
  private void execute(List<T> records) {
    if (CollectionUtil.isEmpty(records)) {
      return;
    }

    logger.debug("Execute records. size=[{}]", records.size());

    try {
      batchExecutor.execute(records);
      LockSupport.unpark(current);
      // logger.info("record pool [{}] is not full,unpark [{}]", name,
      // current);
    } catch (Exception e) {
      logger.error("", e);
      if (exceptionListener != null) {
        logger.error("Execute records failed, calling exception listener...pool=[{}]", name);
        exceptionListener.onException(records);
      }
    }
  }

  /**
   * 记录池是否已满
   * 
   * @return 是否已满
   */
  public boolean full() {
    return getRecordPool().remainCapacity() == 0;
  }

  /**
   * 记录池大小
   * 
   * @return 记录池大小
   */
  public int size() {
    return getRecordPool().size();
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * 获取记录池 @see RecordPool
   * 
   * @return 记录池
   */
  public RecordPool<T> getRecordPool() {
    return recordPoolReference.get();
  }

  /**
   * 设置为多线程工作
   * 
   * @param threads 线程数
   */
  public void setThreads(int threads) {
    this.threads = threads;
    if (scheduler != null) {
      ExecutorUtil.shutdownAndAwait(scheduler);
    }

    this.scheduler = Executors.newScheduledThreadPool(threads,
        new SimpleThreadFactory(DelayExecuteBuffer.class.getSimpleName() + "-multi"));
  }

  public void setCheckInterval(long checkInterval) {
    this.checkInterval = checkInterval;
  }

  public void setBatchSize(int batchSize) {
    this.batchSize = batchSize;
  }

  public void setPoolSize(int poolSize) {
    this.poolSize = poolSize;
  }

  public void setExceptionListener(ExceptionListener<T> exceptionListener) {
    this.exceptionListener = exceptionListener;
  }

  public void setBatchExecutor(BatchExecutor<T> batchExecutor) {
    this.batchExecutor = batchExecutor;
  }

}
