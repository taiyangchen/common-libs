package com.sm.common.libs.bus;

import com.sm.common.libs.core.ToStringSupport;

/**
 * Event
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:31:42
 */
public class Event extends ToStringSupport{

  private final String name;

  public Event(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }

}
