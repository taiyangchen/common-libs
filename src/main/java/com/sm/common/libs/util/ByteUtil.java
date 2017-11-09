package com.sm.common.libs.util;

/**
 * ByteUtil
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年5月21日 下午11:15:59
 */
public abstract class ByteUtil {

  public static byte[] long2Bytes(long num) {
    byte[] byteNum = new byte[8];
    for (int ix = 0; ix < 8; ++ix) {
      int offset = 64 - (ix + 1) * 8;
      byteNum[ix] = (byte) ((num >> offset) & 0xff);
    }
    return byteNum;
  }

  public static long bytes2Long(byte[] byteNum) {
    long num = 0;
    for (int ix = 0; ix < 8; ++ix) {
      num <<= 8;
      num |= (byteNum[ix] & 0xff);
    }
    return num;
  }

  public static byte[] int2Bytes(int num) {
    byte[] byteNum = new byte[4];
    for (int ix = 0; ix < 4; ++ix) {
      int offset = 32 - (ix + 1) * 8;
      byteNum[ix] = (byte) ((num >> offset) & 0xff);
    }
    return byteNum;
  }

  public static int bytes2Int(byte[] byteNum) {
    int num = 0;
    for (int ix = 0; ix < 4; ++ix) {
      num <<= 8;
      num |= (byteNum[ix] & 0xff);
    }
    return num;
  }

}
