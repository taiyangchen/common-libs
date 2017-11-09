/**
 * 
 */
package com.sm.common.libs.codec;

/**
 * 解码器
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月9日 下午9:01:12
 */
public interface Decoder {

  /**
   * 将二进制字节数组解码成对象
   * 
   * @param bytes 二进制字节数组
   * @param clazz 对应的<code>Class</code>类型
   * @return 解码对象
   * @throws DecodeException
   */
  <T> T decode(byte[] bytes, Class<T> clazz) throws DecodeException;

}
