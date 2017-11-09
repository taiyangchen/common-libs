package com.sm.common.libs.bus;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认的消息总线注册器
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:28:41
 * @param <T>
 */
public class DefaultBusRegistry<T> implements BusRegistry<T> {

  /**
   * 分发器的映射表
   */
  private final Map<String, BusDispatcher> busDispatchers = new HashMap<>();

  /**
   * 消息总线管理器 @see BusSignalManager
   */
  private final BusSignalManager<T> signalManager;

  // @Inject
  public DefaultBusRegistry() {
    signalManager = new BusSignalManager<T>(this);
  }

  @Override
  public void bind(BusMember member, String busname) {
    BusDispatcher dispatcher;

    if (busDispatchers.get(busname) == null) {
      dispatcher = new BusDispatcher();
      busDispatchers.put(busname, dispatcher);
    } else {
      dispatcher = busDispatchers.get(busname);
    }

    dispatcher.bind(member);
  }

  @Override
  public void unbind(BusMember member, String busname) {
    BusDispatcher dispatcher = busDispatchers.get(busname);

    if (dispatcher != null) {
      dispatcher.unbind(member);
    }
  }

  @Override
  public void send(String bus, ActionData<T> data) {
    send(bus, data, true);
  }

  @Override
  public void send(String bus, ActionData<T> data, boolean asyn) {
    data = (data != null) ? data : new ActionData<T>();
    data.setBus(bus);
    BusDispatcher dispatcher = busDispatchers.get(bus);
    if (dispatcher != null) {
      dispatcher.push(data, asyn);
    }
  }

  @Override
  public BusPublisherProxy<T> createPublisherProxy() {
    return new BusPublisherProxy<T>(this);
  }

  @Override
  public BusSubscriberProxy<T> createSubscriberProxy(BusListener<T> listener) {
    return new BusSubscriberProxy<T>(listener, this);
  }

  @Override
  public BusSignalManager<T> getSignalManager() {
    return signalManager;
  }

}
