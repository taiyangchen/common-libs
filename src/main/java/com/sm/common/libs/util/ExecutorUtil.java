/**
 * 
 */
package com.sm.common.libs.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * util for jdk executor
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月17日 下午12:15:38
 */
public abstract class ExecutorUtil {

  /**
   * 默认超时时间，1分钟
   */
  private static final int DEFAULT_TIMEOUT = 60;

  /**
   * 在1分钟内关闭<code>executorService</code>，如果不能正常结束，将强制关闭。
   * <p>
   * 如果<code>executorService</code>不存在或已关闭，将不做任何处理。
   * 
   * @param executorService ## @see ExecutorService
   */
  public static final void shutdownAndAwait(ExecutorService executorService) {

    shutdownAndAwait(executorService, DEFAULT_TIMEOUT, TimeUnit.SECONDS);

  }

  /**
   * 关闭<code>executorService</code>，如果<code>executorService</code>
   * 不存在或已关闭，将不做任何处理。在限定时间内如无法正常结束，将强制关闭。
   * 
   * @param executorService ## @see ExecutorService
   * @param timeout 限定时间
   * @param timeUnit 单位 @see TimeUnit
   */
  public static final void shutdownAndAwait(ExecutorService executorService, long timeout,
      TimeUnit timeUnit) {
    if (isShutDown(executorService)) {
      return;
    }

    executorService.shutdown();
    try {
      if (!executorService.awaitTermination(timeout, timeUnit)) {
        executorService.shutdownNow();
      }
    } catch (InterruptedException ie) {
      executorService.shutdownNow();
      Thread.currentThread().interrupt();
    }
    executorService.shutdownNow();

  }

  /**
   * <code>executorService</code>是否已关闭，如果<code>executorService</code>不存在，也当做“已关闭”对待
   * 
   * @param executorService ## @see ExecutorService
   * @return 如果<code>executorService</code>“已关闭”，则返回<code>true</code>
   */
  public static boolean isShutDown(ExecutorService executorService) {
    return (executorService == null || executorService.isShutdown());
  }

}
