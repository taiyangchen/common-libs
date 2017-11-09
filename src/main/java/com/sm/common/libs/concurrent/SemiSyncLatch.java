
package com.sm.common.libs.concurrent;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.sm.common.libs.able.Stopable;
import com.sm.common.libs.core.DefaultCaller;
import com.sm.common.libs.core.ResponseFuture;
import com.sm.common.libs.core.SimpleThreadFactory;
import com.sm.common.libs.util.ExecutorUtil;

/**
 * 半同步的门闩，比如MySql的主从同步
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年12月1日 下午3:15:25
 */
public class SemiSyncLatch implements Stopable {

  private ExecutorService executor;

  public SemiSyncLatch() {
    executor = Executors.newCachedThreadPool(new SimpleThreadFactory(this.getClass().getSimpleName()));
  }

  public SemiSyncLatch(ExecutorService executor) {
    this.executor = executor;
  }

  public <T> T executeAndWait(Collection<Callable<T>> callables) throws InterruptedException, ExecutionException {
    final ResponseFuture<T> futureResponse = new ResponseFuture<T>();
    for (final Callable<T> callable : callables) {
      executor.execute(new Runnable() {
        @Override
        public void run() {
          futureResponse.set(new DefaultCaller<T>(callable).call());
        }
      });
    }

    return futureResponse.get();
  }

  public <T> T executeAndWait(final Callable<T> callable, int workers) throws InterruptedException, ExecutionException {
    if (workers <= 0) {
      throw new IllegalArgumentException("workers must > 0");
    }

    final ResponseFuture<T> futureResponse = new ResponseFuture<T>();
    for (int i = 0; i < workers; i++) {
      executor.execute(new Runnable() {
        @Override
        public void run() {
          futureResponse.set(new DefaultCaller<T>(callable).call());
        }
      });
    }

    return futureResponse.get();
  }

  public <T> T executeAndWait(Collection<Callable<T>> callables, long timeout, TimeUnit unit)
      throws InterruptedException, ExecutionException, TimeoutException {
    final ResponseFuture<T> futureResponse = new ResponseFuture<T>();
    for (final Callable<T> callable : callables) {
      executor.execute(new Runnable() {
        @Override
        public void run() {
          futureResponse.set(new DefaultCaller<T>(callable).call());
        }
      });
    }

    return futureResponse.get();
  }

  public <T> T executeAndWait(final Callable<T> callable, int workers, long timeout, TimeUnit unit)
      throws InterruptedException, ExecutionException, TimeoutException {
    if (workers <= 0) {
      throw new IllegalArgumentException("workers must > 0");
    }

    final ResponseFuture<T> futureResponse = new ResponseFuture<T>();
    for (int i = 0; i < workers; i++) {
      executor.execute(new Runnable() {
        @Override
        public void run() {
          futureResponse.set(new DefaultCaller<T>(callable).call());
        }
      });
    }

    return futureResponse.get();
  }

  @Override
  public void stop() {
    ExecutorUtil.shutdownAndAwait(executor);
  }
}
