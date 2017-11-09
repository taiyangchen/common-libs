/**
 * 
 */
package com.sm.common.libs.concurrent;

import java.util.Random;

/**
 * 回退休眠，减少锁竞争
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年12月1日 下午3:08:46
 */
public class Backoff {

  /**
   * 休眠下限
   */
  private final int minDelay;

  /**
   * 休眠上线
   */
  private final int maxDelay;

  /**
   * 休眠时间，在<code>limit</code>和<code>2*limit</code>之间
   */
  private int limit;

  /**
   * 休眠随机时间
   */
  private final Random random; // add randomness to wait

  /**
   * 设置休眠上下限
   * 
   * @param min 休眠下限
   * @param max 休眠上限
   */
  public Backoff(int min, int max) {
    if (max < min) {
      throw new IllegalArgumentException("max must be greater than min");
    }
    minDelay = min;
    maxDelay = min;
    limit = minDelay;
    random = new Random();
  }

  /**
   * 休眠，减少CPU竞争
   * 
   * @throws InterruptedException
   */
  public void backoff() throws InterruptedException {
    int delay = random.nextInt(limit);
    if (limit < maxDelay) { // double limit if less than max
      limit = 2 * limit;
    }

    Thread.sleep(delay);
  }
}
