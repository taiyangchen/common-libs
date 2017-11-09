/**
 * 
 */
package com.sm.common.libs.concurrent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * FIFOMutex
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年11月4日 上午12:04:04
 */
public class FIFOMutex {

  private final AtomicBoolean locked = new AtomicBoolean(false);
  private final Queue<Thread> waiters = new ConcurrentLinkedQueue<Thread>();

  public void lock() {
    boolean wasInterrupted = false;
    Thread current = Thread.currentThread();
    waiters.add(current);
    // Block while not first in queue or cannot acquire lock
    while (waiters.peek() != current || !locked.compareAndSet(false, true)) {
      LockSupport.park(this);
      if (Thread.interrupted()) {// ignore interrupts while waiting
        wasInterrupted = true;
      }

    }
    waiters.remove();
    if (wasInterrupted) {// reassert interrupt status on exit
      current.interrupt();
    }

  }

  public void unlock() {
    locked.set(false);
    LockSupport.unpark(waiters.peek());
  }

}
