package com.sm.common.libs.able;

/**
 * 消息接收
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月9日 下午8:47:42
 * @param <T>
 */
public interface Receiver<T> {

  /**
   * 接受消息
   * 
   * @param message 接收信息附带对象
   */
  void messageReceived(T message);
}
