package com.sm.common.libs.bus;

/**
 * 消息总线注册器
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:27:01
 * @param <T>
 */
public interface BusRegistry<T> {

  /**
   * 默认总线名称
   */
  String BUS_UNDEFINED = "BUS_UNDEFINED";

  /**
   * 绑定消息到总线
   * 
   * @param member 消息成员
   * @param busname 总线名称
   */
  void bind(BusMember member, String busname);

  /**
   * 解绑
   * 
   * @param member 消息成员
   * @param busname 总线名称
   */
  void unbind(BusMember member, String busname);

  /**
   * 发送消息
   * 
   * @param bus 总线名称
   * @param data 数据 @see ActionData
   */
  void send(String bus, ActionData<T> data);

  /**
   * 发送消息
   * 
   * @param bus 总线名称
   * @param data 数据 @see ActionData
   * @param notifyConcurrent 是否异步
   */
  void send(String bus, ActionData<T> data, boolean asyn);

  /**
   * 创建总线发布代理 @see BusPublisherProxy
   * 
   * @return 总线发布代理
   */
  BusPublisherProxy<T> createPublisherProxy();

  /**
   * 创建总线发布代理 @see BusSubscriberProxy
   * 
   * @return 创建总线发布代理
   */
  BusSubscriberProxy<T> createSubscriberProxy(BusListener<T> listener);

  /**
   * 获取消息总线管理器 @see BusSignalManager
   * 
   * @return 消息总线管理器
   */
  BusSignalManager<T> getSignalManager();

}
