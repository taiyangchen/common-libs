package com.sm.common.libs.core;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.ArrayUtils;

import com.sm.common.libs.util.ExceptionUtil;
import com.sm.common.libs.util.IOUtil;

/**
 * 生成唯一ID。
 * <p>
 * 唯一ID由以下元素构成：<code>machineId-jvmId-timestamp-counter</code>。
 * </p>
 * <p>
 * 默认情况下，UUID由数字和大写字母构成。如果在构造函数时，指定<code>noCase=false</code> ，那么所生成的ID将包含小写字母，这样ID的长度会较短。
 * </p>
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年12月10日 上午2:13:46
 */
public class UUID {

  private static final char[] DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
  private static final char[] DIGITS_NOCASE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

  private boolean noCase;
  private String instanceId;
  private AtomicInteger counter;

  public UUID() {
    this(true);
  }

  public UUID(boolean noCase) {
    // 1. Machine ID - 根据IP/MAC区分
    byte[] machineId = getLocalHostAddress();

    // 2. JVM ID - 根据启动时间区分 + 随机数
    byte[] jvmId = getRandomizedTime();

    this.instanceId = bytesToString(machineId, noCase) + "-" + bytesToString(jvmId, noCase);

    // counter
    this.counter = new AtomicInteger();

    this.noCase = noCase;
  }

  /** 取得local host的地址，如果有可能，取得物理MAC地址。 */
  private static byte[] getLocalHostAddress() {
    Method getHardwareAddress;

    try {
      getHardwareAddress = NetworkInterface.class.getMethod("getHardwareAddress");
    } catch (Exception e) {
      getHardwareAddress = null;
    }

    byte[] addr;

    try {
      InetAddress localHost = InetAddress.getLocalHost();

      if (getHardwareAddress != null) {
        addr = (byte[]) getHardwareAddress.invoke(NetworkInterface.getByInetAddress(localHost)); // maybe
                                                                                                 // null
      } else {
        addr = localHost.getAddress();
      }
    } catch (Exception e) {
      addr = null;
    }

    if (addr == null) {
      addr = new byte[] {127, 0, 0, 1};
    }

    return addr;
  }

  /** 取得当前时间，加上随机数。 */
  private byte[] getRandomizedTime() {
    long jvmId = System.currentTimeMillis();
    long random = new SecureRandom().nextLong();

    // 取得上述ID的bytes，并转化成字符串
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);

    try {
      dos.writeLong(jvmId);
      dos.writeLong(random);
      return baos.toByteArray();
    } catch (Exception e) {
      throw ExceptionUtil.toRuntimeException(e);
    } finally {
      IOUtil.close(dos);
    }

  }

  /**
   * <code>System.nanoTime()</code>虽能避免使用自旋锁提供唯一ID，但自身非常耗性能<br>
   * 不如<code>System.currentTimeMillis()</code>与自旋锁配合性能高
   * 
   * @return 下一个唯一ID
   */
  public String nextID() {
    // MACHINE_ID + JVM_ID + 当前时间 + counter
    return instanceId + "-" + longToString(System.currentTimeMillis(), noCase) + "-"
        + longToString(counter.getAndIncrement(), noCase);
  }

  /**
   * 将一个byte数组转换成62进制的字符串。
   * 
   * @param bytes 二进制数组
   * @return 62进制的字符串
   */
  public static String bytesToString(byte[] bytes) {
    return bytesToString(bytes, false);
  }

  /**
   * 将一个byte数组转换成62进制的字符串。
   * 
   * @param bytes 二进制数组
   * @param noCase 区分大小写
   * @return 62进制的字符串
   */
  public static String bytesToString(byte[] bytes, boolean noCase) {
    char[] digits = noCase ? DIGITS_NOCASE : DIGITS;
    int digitsLength = digits.length;

    if (ArrayUtils.isEmpty(bytes)) {
      return String.valueOf(digits[0]);
    }

    StringBuilder strValue = new StringBuilder();
    int value = 0;
    int limit = Integer.MAX_VALUE >>> 8;
    int i = 0;

    do {
      while (i < bytes.length && value < limit) {
        value = (value << 8) + (0xFF & bytes[i++]);
      }

      while (value >= digitsLength) {
        strValue.append(digits[value % digitsLength]);
        value = value / digitsLength;
      }
    } while (i < bytes.length);

    if (value != 0 || strValue.length() == 0) {
      strValue.append(digits[value]);
    }

    return strValue.toString();
  }

  /**
   * 将一个长整形转换成62进制的字符串。
   * 
   * @param longValue 64位数字
   * @return 62进制的字符串
   */
  public static String longToString(long longValue) {
    return longToString(longValue, false);
  }

  /**
   * 将一个长整形转换成62进制的字符串。
   * 
   * @param longValue 64位数字
   * @param noCase 区分大小写
   * @return 62进制的字符串
   */
  public static String longToString(long longValue, boolean noCase) {
    char[] digits = noCase ? DIGITS_NOCASE : DIGITS;
    int digitsLength = digits.length;

    if (longValue == 0) {
      return String.valueOf(digits[0]);
    }

    if (longValue < 0) {
      longValue = -longValue;
    }

    StringBuilder strValue = new StringBuilder();

    while (longValue != 0) {
      int digit = (int) (longValue % digitsLength);
      longValue = longValue / digitsLength;

      strValue.append(digits[digit]);
    }

    return strValue.toString();
  }

}
