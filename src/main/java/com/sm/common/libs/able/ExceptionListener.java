package com.sm.common.libs.able;

/**
 * 异常监听器
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月9日 上午10:44:42
 */
public interface ExceptionListener {

  /**
   * 异常处理
   * 
   * @param e 异常
   */
  void onException(Throwable e);

}
