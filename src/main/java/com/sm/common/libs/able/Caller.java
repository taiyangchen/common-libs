package com.sm.common.libs.able;

import java.util.concurrent.Callable;

/**
 * 无抛出异常的“Callable”
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年12月1日 下午3:18:59
 * @param <V>
 */
public interface Caller<V> extends Callable<V> {

  /**
   * 计算结果
   * 
   * @return 计算结果
   */
  @Override
  V call();

}
