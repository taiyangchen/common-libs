package com.sm.common.libs.bus;

import org.junit.Test;

/**
 * BusTest
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:31:33
 */
public class BusObjectTest {

  @Test
  public void test() {
    BusRegistry<Object> busRegistry = new DefaultBusRegistry<Object>();
    new BusObjectReceiver(busRegistry.getSignalManager());

    BusObjectSender sender = new BusObjectSender(busRegistry.getSignalManager());
    sender.send(false);
    System.out.println("done");
  }
  
}
