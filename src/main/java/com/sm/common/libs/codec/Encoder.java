/**
 * 
 */
package com.sm.common.libs.codec;

/**
 * 编码器
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月9日 下午9:01:02
 */
public interface Encoder {

  /**
   * 将对象编码成二进制数组
   * 
   * @param object 编码对象
   * @return 二进制数组
   * @throws EncodeException
   */
  byte[] encode(Object object) throws EncodeException;

}
