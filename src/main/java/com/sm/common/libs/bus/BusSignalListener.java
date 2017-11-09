package com.sm.common.libs.bus;

/**
 * 总线监听器
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:27:52
 * @param <T>
 */
public interface BusSignalListener<T> {

  /**
   * 发送数据
   * 
   * @param signal 数据
   */
  void signalFired(T signal);
}
