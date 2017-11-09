package com.sm.common.libs.bus;

import com.sm.common.libs.bus.BusSignalManager;

/**
 * BusObjectSender
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:31:57
 */
public class BusObjectSender {

  private final BusSignalManager<Object> bsm;

  public BusObjectSender(BusSignalManager<Object> bsm) {
    this.bsm = bsm;
  }

  public void send() {
    bsm.signal(new Event("test"));
    bsm.signal("test");
    bsm.signal("xxx");
    bsm.signal(new Event("xxx"));
  }

  public void send(boolean asyn) {
    bsm.signal(new Event("test"), asyn);
    bsm.signal("test", asyn);
    bsm.signal("xxx", asyn);
    bsm.signal(new Event("xxx"), asyn);
  }

}
