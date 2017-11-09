package com.sm.common.libs.bus;

import com.sm.common.libs.bus.BusSignalListener;
import com.sm.common.libs.bus.BusSignalManager;

/**
 * BusReceiver
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:31:50
 */
public class BusReceiver implements BusSignalListener<Event> {

  public BusReceiver(BusSignalManager<Event> bsm) {
    bsm.bind(Event.class, this);
  }

  @Override
  public void signalFired(Event signal) {
    System.out.println("signal-->" + signal.getName());
  }

}
