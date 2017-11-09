package com.sm.common.libs.bus;

/**
 * 总线发布代理
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:26:48
 * @param <T>
 */
public class BusPublisherProxy<T> extends AbstractBusMember<T> implements BusPublisher<T>, BusListener<T> {

  public BusPublisherProxy(BusRegistry<T> registry) {
    super(registry);
  }

  @Override
  public void receive(ActionData<T> event) throws ActionException {
    // nothing
  }

  @Override
  public void send(String bus, ActionData<T> data) {
    registry.send(bus, data);
  }

  /**
   * 发布
   * 
   * @param busName 总线类型
   * @param data 携带数据 @see ActionData
   * @param asyn 是否异步
   */
  public void send(String bus, ActionData<T> data, boolean asyn) {
    registry.send(bus, data, asyn);
  }

  /**
   * 发布
   * 
   * @param data 携带数据 @see ActionData
   */
  public void send(ActionData<T> data) {
    if (data == null) {
      logger.warn("send() null parameter");
      return;
    }

    final String bus = data.getBus();
    if (bus == null) {
      logger.warn("send() Logical bus not set.  The following data was not sent: {}", data.getPayload());
      return;
    }

    registry.send(bus, data);
  }

  /**
   * 发布
   * 
   * @param data 携带数据 @see ActionData
   * @param asyn 是否异步
   */
  public void send(ActionData<T> data, boolean asyn) {
    if (data == null) {
      logger.warn("send() null parameter");
      return;
    }

    final String bus = data.getBus();

    if (bus == null) {
      logger.warn("send() Logical bus not set.  The following data was not sent: {}", data.getPayload());
      return;
    }

    registry.send(bus, data, asyn);
  }

}
