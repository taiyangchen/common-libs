package com.sm.common.libs.bus;

/**
 * 发布主题消息
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:22:57
 * @param <T>
 */
public interface BusPublisher<T> {
  /**
   * 发布
   * 
   * @param busName 总线类型
   * @param data 携带数据 @see ActionData
   */
  void send(String busName, ActionData<T> data);
}
