/**
 * 
 */
package com.sm.common.libs.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * JMX相关的工具类
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月25日 下午4:24:37
 */
public abstract class JmxUtil {

  /**
   * 获取当前进程号
   * 
   * @return 当前进程号
   */
  public static int getPid() {
    RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
    String name = runtime.getName();
    int index = name.indexOf("@");
    if (index != -1) {
      return Integer.parseInt(name.substring(0, index));
    }

    return -1;
  }

}
