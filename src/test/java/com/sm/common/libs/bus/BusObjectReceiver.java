package com.sm.common.libs.bus;

import com.sm.common.libs.bus.BusSignalListener;
import com.sm.common.libs.bus.BusSignalManager;

/**
 * BusReceiver
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:31:50
 */
public class BusObjectReceiver implements BusSignalListener<Object> {

  public BusObjectReceiver(BusSignalManager<Object> bsm) {
    bsm.bind(Event.class, this);
    bsm.bind(String.class, this);
  }

  @Override
  public void signalFired(Object signal) {
    System.out.println("signal-->" + signal + " type-->" + signal.getClass());
  }

}
