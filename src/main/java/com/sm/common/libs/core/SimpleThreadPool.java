package com.sm.common.libs.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * simple thread pool FIXME
 * 
 * @author <a href="mailto:xuchen06@baidu.com">xuc</a>
 * @version create on 2015-1-12 下午10:54:25
 */
public class SimpleThreadPool extends LoggerSupport {

  private static SimpleThreadPool instance = new SimpleThreadPool();

  private final ExecutorService pool;

  private SimpleThreadPool() {
    pool = Executors.newCachedThreadPool(new SimpleThreadFactory("ThreadPool"));
  }

  public static SimpleThreadPool getInstance() {
    return instance;
  }

  public void execute(String name, Runnable runnable) {
    logger.debug("Executing thread: " + name);
    try {
      pool.execute(runnable);
    } catch (RuntimeException e) {
      logger.error("Trapped exception on thread: " + name, e);
    }
  }

}
