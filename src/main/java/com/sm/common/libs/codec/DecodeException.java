/**
 * 
 */
package com.sm.common.libs.codec;

import com.sm.common.libs.exception.MessageException;

/**
 * 解码异常
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 下午2:58:16
 */
public class DecodeException extends MessageException {

  /**
   * 
   */
  private static final long serialVersionUID = -4128289234956207576L;

  /**
   * 构造一个空的异常.
   */
  public DecodeException() {
    super();
  }

  /**
   * 构造一个异常, 指明异常的详细信息.
   * 
   * @param message 详细信息
   */
  public DecodeException(String message) {
    super(message);
  }

  /**
   * 构造一个异常, 指明引起这个异常的起因.
   * 
   * @param cause 异常的起因
   */
  public DecodeException(Throwable cause) {
    super(cause);
  }

  /**
   * 构造一个异常, 指明引起这个异常的起因.
   * 
   * @param message 详细信息
   * @param cause 异常的起因
   */
  public DecodeException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * 构造一个异常, 参数化详细信息
   * 
   * @param message 详细信息
   * @param params 参数表
   */
  public DecodeException(String message, Object... params) {
    super(message, params);
  }

  /**
   * 构造一个异常, 参数化详细信息,指明引起这个异常的起因
   * 
   * @param message 详细信息
   * @param cause 异常的起因
   * @param params 参数表
   */
  public DecodeException(String message, Throwable cause, Object... params) {
    super(message, cause, params);
  }

}
