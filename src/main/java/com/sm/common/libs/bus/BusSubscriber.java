package com.sm.common.libs.bus;

/**
 * 总线信息订阅
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:28:25
 * @param <T>
 */
public interface BusSubscriber<T> extends BusMember {

  /**
   * 消息接收
   * 
   * @param data 数据
   * @throws ActionException
   */
  void receive(ActionData<T> data) throws ActionException;
}
