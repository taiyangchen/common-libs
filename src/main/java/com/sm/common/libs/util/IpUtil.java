/**
 * 
 */
package com.sm.common.libs.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IP地址相关的工具类
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月18日 上午10:48:37
 */
public abstract class IpUtil {

  private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);

  /**
   * 获取本机对外4位IP地址
   * 
   * @return 本机对外4位IP地址
   */
  public static String getLocalV4Ip() {
    String ip = "127.0.0.1";
    List<String> ips = getLocalV4Ips();
    if (CollectionUtil.isEmpty(ips)) {
      return ip;
    }

    Collections.sort(ips);
    for (String localIp : ips) {
      if (localIp.startsWith("127.")) {
        continue;
      }
      if (localIp.startsWith("192.168")) {
        ip = get192168(ip, localIp);
        continue;
      }
      if (localIp.startsWith("10.")) {
        ip = get10(ip, localIp);
        continue;
      }

      ip = localIp;
    }

    return ip;
  }

  /**
   * 获取"10."开头的本地IP地址
   * 
   * @param ip 待处理IP
   * @param localIp 本地IP
   * @return "10."开头的本地IP地址
   */
  private static String get10(String ip, String localIp) {
    if (ip.startsWith("127.")) {
      return localIp;
    }
    if (ip.startsWith("10.") && ip.endsWith(".1")) {
      return localIp;
    }
    if (!localIp.startsWith("10.") && !localIp.endsWith(".1")) {
      return localIp;
    }
    return ip;
  }

  /**
   * 获取"192.168"开头的本地IP地址
   * 
   * @param ip 待处理IP
   * @param localIp 本地IP
   * @return "192.168"开头的本地IP地址
   */
  private static String get192168(String ip, String localIp) {
    if (ip.startsWith("127.")) {
      return localIp;
    }
    if (ip.startsWith("192.168") && ip.endsWith(".1")) {
      return localIp;
    }
    if (!localIp.startsWith("192.168") && !localIp.endsWith(".1")) {
      return localIp;
    }

    return ip;
  }

  /**
   * 获取本机ipv4地址列表
   * 
   * @return 本机ipv4地址列表
   */
  private static List<String> getLocalV4Ips() {
    List<String> ips = new ArrayList<>();
    try {
      Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
      while (interfaces.hasMoreElements()) {
        NetworkInterface ni = interfaces.nextElement();
        if (ni.isLoopback() || ni.isVirtual() || !ni.isUp()) {
          continue;
        }
        String name = ni.getDisplayName();
        if (name == null || !name.contains("Loopback")) {
          addLocalIp(ips, ni);
        }
      }
    } catch (SocketException e) {
      logger.error("localips error", e);
    }
    return ips;
  }

  /**
   * 将网络接口中的本地IP地址添加到IP列表中
   * 
   * @param ips IP列表
   * @param ni 网络接口 @see NetworkInterface
   */
  private static void addLocalIp(List<String> ips, NetworkInterface ni) {
    Enumeration<InetAddress> addresses = ni.getInetAddresses();
    while (addresses.hasMoreElements()) {
      InetAddress address = addresses.nextElement();
      String ip = address.getHostAddress();
      if (!ip.contains(":")) {
        ips.add(ip);
      }
    }
  }

}
