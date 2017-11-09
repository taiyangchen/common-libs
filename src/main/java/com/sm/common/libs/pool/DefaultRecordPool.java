package com.sm.common.libs.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.sm.common.libs.core.LoggerSupport;

/**
 * 默认的记录池
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月16日 下午5:28:32
 * @param <T>
 */
public class DefaultRecordPool<T> extends LoggerSupport implements RecordPool<T> {

  /**
   * 阻塞队列
   */
  private BlockingQueue<T> queue;

  /**
   * 批处理数
   */
  private int batchSize = 500;

  public DefaultRecordPool(int poolSize) {
    queue = new LinkedBlockingQueue<T>(poolSize);
  }

  @Override
  public boolean add(T rec) {
    if (rec == null) {
      logger.warn("object is null,ignore.");
      return false;
    }

    boolean ret = queue.offer(rec);
    if (!ret) {
      logger.warn("add the object to the queue failed, the queue may be full.");
    }

    return ret;
  }

  @Override
  public boolean add(T rec, long timeout, TimeUnit unit) throws InterruptedException {
    if (rec == null) {
      logger.warn("object is null,ignore.");
      return false;
    }

    boolean ret = queue.offer(rec, timeout, unit);
    if (!ret) {
      logger.warn("add the object to the queue failed, the queue may be full.");
    }

    return ret;
  }

  @Override
  public List<T> asList() {
    List<T> recordsCopy = new ArrayList<>();
    if (queue.size() > 0) {
      int num = queue.size() >= batchSize ? batchSize : queue.size();
      queue.drainTo(recordsCopy, num);
    }

    return recordsCopy;
  }

  @Override
  public List<T> getWholeRecords() {
    List<T> recordsCopy = new ArrayList<>();
    int num = queue.size();
    queue.drainTo(recordsCopy, num);

    return recordsCopy;
  }

  @Override
  public void setBatchSize(int batchSize) {
    this.batchSize = batchSize;
  }

  @Override
  public int remainCapacity() {
    return this.queue.remainingCapacity();
  }

  @Override
  public int size() {
    return this.queue.size();
  }

  public void setQueue(BlockingQueue<T> queue) {
    this.queue = queue;
  }

}
