package com.sm.common.libs.bus;

/**
 * 订阅代理
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:28:33
 * @param <T>
 */
public class BusSubscriberProxy<T> extends AbstractBusMember<T> implements BusSubscriber<T> {

  /**
   * 消息接听器
   */
  protected BusListener<T> listener;

  public BusSubscriberProxy(BusListener<T> listener, BusRegistry<T> registry) {
    super(listener, registry);
  }

  @Override
  public void receive(ActionData<T> data) throws ActionException {
    final BusListener<T> listener = getBusListener();
    if (listener != null) {
      listener.receive(data);
    }
  }

}
