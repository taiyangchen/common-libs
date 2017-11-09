package com.sm.common.libs.core;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 异步响应对象
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月4日 下午2:43:52
 * @param <V>
 */
public class ResponseFuture<V> extends FutureTask<V> {

  public ResponseFuture() {
    super(new Callable<V>() {
      public V call() throws Exception {
        return null;
      }
    });
  }

  @Override
  public void set(V v) {
    super.set(v);
  }
}
