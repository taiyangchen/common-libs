package com.sm.common.libs.bus;

/**
 * 消息接听器
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:22:13
 * @param <T>
 */
public interface BusListener<T> {
  /**
   * 接收消息
   * 
   * @param event 事件 @see ActionData
   * @throws ActionException
   */
  void receive(ActionData<T> event) throws ActionException;
}
