package com.sm.common.libs.bus;

import com.sm.common.libs.able.Receiver;

/**
 * 消息接收器
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:29:41
 * @param <T>
 */
public class ReceiveListener<T> implements BusSignalListener<T> {

  /**
   * 消息接收 @see Receiver
   */
  private Receiver<T> receiver;

  public ReceiveListener(BusRegistry<T> registry, Class<T> clazz) {
    registry.getSignalManager().bind(clazz, this);
  }

  @Override
  public void signalFired(T signal) {
    receiver.messageReceived(signal);
  }

  public void setReceiver(Receiver<T> receiver) {
    this.receiver = receiver;
  }

}
