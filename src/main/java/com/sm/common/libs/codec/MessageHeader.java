/**
 * 
 */
package com.sm.common.libs.codec;

/**
 * 协议头
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午12:38:18
 */
public interface MessageHeader {

  /**
   * header的长度
   * 
   * @return header的长度
   */
  int getHeaderSize();

  /**
   * 后续body的长度
   * 
   * @return 后续body的长度
   */
  int getBodySize();

  /**
   * 设置body长度
   * 
   * @param bodySize 后续body的长度
   */
  void setBodySize(int bodySize);

  /**
   * 消息唯一ID
   * 
   * @return 消息ID
   */
  int getId();

  /**
   * 设置消息唯一ID
   * 
   * @param id 消息唯一ID
   */
  void setId(int id);

  /**
   * 消息类型
   * 
   * @return 消息类型
   */
  int getType();

  /**
   * 设置消息类型，基本分 request、response、notify等
   * 
   * @param type 消息类型
   */
  void setType(int type);

  /**
   * 类类型
   * 
   * @return 类类型
   */
  Class<?> getClassType();

  /**
   * 设置消息类类型
   * 
   * @param type 消息类类型
   */
  void setClassType(Class<?> type);

  /**
   * header对象转换为二进制数组
   * 
   * @return 二进制数组
   */
  byte[] toArray();

  /**
   * 由二进制数据组转换为对象
   * 
   * @param array 二进制数组
   * @throws ProtocolException
   */
  void parseForm(byte[] array) throws ProtocolException;

}
