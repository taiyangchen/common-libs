package com.sm.common.libs.able;

/**
 * 消息发送接口
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月17日 下午3:07:43
 * @param <T>
 */
public interface Sender<T> {

  /**
   * 发送消息
   * 
   * @param message 发送信息
   */
  void sendMessage(T message);

}
