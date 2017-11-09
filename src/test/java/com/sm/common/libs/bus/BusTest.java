package com.sm.common.libs.bus;

import org.junit.Test;

import com.sm.common.libs.bus.BusRegistry;
import com.sm.common.libs.bus.DefaultBusRegistry;

/**
 * BusTest
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:31:33
 */
public class BusTest {

  @Test
  public void test() {
    BusRegistry<Event> busRegistry = new DefaultBusRegistry<Event>();
    new BusReceiver(busRegistry.getSignalManager());

    BusSender sender = new BusSender(busRegistry.getSignalManager());
    sender.send(true);
    System.out.println("done");
  }

}
