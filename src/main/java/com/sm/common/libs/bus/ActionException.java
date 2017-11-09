package com.sm.common.libs.bus;

import com.sm.common.libs.exception.MessageException;
import com.sm.common.libs.util.MessageUtil;

/**
 * 消息发送后接收时异常
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:19:33
 */
public class ActionException extends MessageException {

  private static final long serialVersionUID = 5020448198119099567L;

  /**
   * 构造一个空的异常.
   */
  public ActionException() {
    super();
  }

  /**
   * 构造一个异常, 指明异常的详细信息.
   * 
   * @param message 详细信息
   */
  public ActionException(String message) {
    super(message);
  }

  /**
   * 构造一个异常, 指明引起这个异常的起因.
   * 
   * @param cause 异常的起因
   */
  public ActionException(Throwable cause) {
    super(cause);
  }

  /**
   * 构造一个异常, 指明引起这个异常的起因.
   * 
   * @param message 详细信息
   * @param cause 异常的起因
   */
  public ActionException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * 构造一个异常, 参数化详细信息
   * 
   * @param message 详细信息
   * @param params 参数表
   */
  public ActionException(String message, Object... params) {
    super(MessageUtil.formatLogMessage(message, params));
  }

  /**
   * 构造一个异常, 参数化详细信息,指明引起这个异常的起因
   * 
   * @param message 详细信息
   * @param cause 异常的起因
   * @param params 参数表
   */
  public ActionException(String message, Throwable cause, Object... params) {
    super(MessageUtil.formatLogMessage(message, params), cause);
  }

}
