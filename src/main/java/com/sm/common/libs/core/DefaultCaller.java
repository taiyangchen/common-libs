package com.sm.common.libs.core;

import java.util.concurrent.Callable;

import com.sm.common.libs.able.Caller;
import com.sm.common.libs.util.ExceptionUtil;

/**
 * 默认的<code>Caller</code>实现
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年12月1日 下午3:20:03
 * @param <V>
 */
public class DefaultCaller<V> implements Caller<V> {

  private Callable<V> callable;

  public DefaultCaller(Callable<V> callable) {
    this.callable = callable;
  }

  @Override
  public V call() {
    try {
      return callable.call();
    } catch (Exception e) {
      throw ExceptionUtil.toRuntimeException(e);
    }
  }

}
