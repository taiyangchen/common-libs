package com.sm.common.libs.bus;

import com.sm.common.libs.bus.BusSignalManager;

/**
 * BusSender
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:31:57
 */
public class BusSender {

  private final BusSignalManager<Event> bsm;

  public BusSender(BusSignalManager<Event> bsm) {
    this.bsm = bsm;
  }

  public void send() {
    bsm.signal(new Event("test"));
  }

  public void send(boolean asyn) {
    bsm.signal(new Event("test"), asyn);
  }

}
