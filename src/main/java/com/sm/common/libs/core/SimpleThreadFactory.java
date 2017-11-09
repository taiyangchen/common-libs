package com.sm.common.libs.core;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 简单的<code>ThreadFactory</code>接口实现
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月9日 下午3:30:21
 */
public class SimpleThreadFactory implements ThreadFactory {

  private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
  private final ThreadGroup group;
  private final AtomicInteger threadNumber = new AtomicInteger(1);
  private final String namePrefix;

  private UncaughtExceptionHandler exceptionHandler;

  public static ThreadFactory create(String name) {
    return new SimpleThreadFactory(name);
  }

  public SimpleThreadFactory(String name) {
    SecurityManager s = System.getSecurityManager();
    group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
    namePrefix = name + "-" + POOL_NUMBER.getAndIncrement() + "-thread-";
  }

  public SimpleThreadFactory(String name, UncaughtExceptionHandler exceptionHandler) {
    this(name);
    this.exceptionHandler = exceptionHandler;
  }

  @Override
  public Thread newThread(Runnable r) {
    Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
    if (t.isDaemon()) {
      t.setDaemon(false);
    }

    if (t.getPriority() != Thread.NORM_PRIORITY) {
      t.setPriority(Thread.NORM_PRIORITY);
    }

    if (exceptionHandler != null) {
      t.setUncaughtExceptionHandler(exceptionHandler);
    }

    return t;
  }

}
