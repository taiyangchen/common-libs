/**
 * 
 */
package com.sm.common.libs.codec;

/**
 * 编解码器
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月9日 下午9:01:35
 */
public interface MessageCodec extends Encoder, Decoder {

  /**
   * 获取协议头信息
   * 
   * @return 协议头信息 @see MessageHeader
   */
  MessageHeader getHeader();

}
