package com.sm.common.libs.bus;

import java.util.ArrayList;
import java.util.List;

import com.sm.common.libs.core.LoggerSupport;

/**
 * 总线成员的抽象类
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:13:28
 * @param <T>
 */
public abstract class AbstractBusMember<T> extends LoggerSupport implements BusMember {

  /**
   * 消息类型
   */
  private List<String> registeredBuses;

  /**
   * 处理器
   */
  private Object handle;

  /**
   * 消息总线监听器 @see BusListener
   */
  private BusListener<T> listener;

  /**
   * 消息总线注册器 @see BusRegistry
   */
  protected BusRegistry<T> registry;

  public AbstractBusMember(BusRegistry<T> registry) {
    registeredBuses = new ArrayList<>();
    this.registry = registry;
  }

  public AbstractBusMember(BusListener<T> listener, BusRegistry<T> registry) {
    registeredBuses = new ArrayList<>();
    this.registry = registry;
    this.listener = listener;
  }

  @Override
  public void bind(String busName) {
    registeredBuses.add(busName);
    registry.bind(this, busName);
  }

  @Override
  public void unbind(String busName) {
    registeredBuses.remove(busName);
    registry.unbind(this, busName);
  }

  @Override
  public List<String> getRegisteredBuses() {
    return registeredBuses;
  }

  @Override
  public Object getMemberHandle() {
    return handle;
  }

  @Override
  public void setMemberHandle(Object handle) {
    this.handle = handle;
  }

  public BusListener<T> getBusListener() {
    return listener;
  }

  public void setBusListener(BusListener<T> listener) {
    this.listener = listener;
  }

  public BusRegistry<T> getBusRegistry() {
    return registry;
  }

}
