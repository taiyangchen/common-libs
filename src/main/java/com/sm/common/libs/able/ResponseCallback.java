package com.sm.common.libs.able;

/**
 * 回调接口
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月9日 上午10:46:36
 * @param <T>
 */
public interface ResponseCallback<T> extends ExceptionListener {

  /**
   * 处理成功的回调
   * 
   * @param result 回调对象
   */
  void onSuccess(T result);

}
