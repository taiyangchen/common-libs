/**
 * 
 */
package com.sm.common.libs.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 处理异常的工具类。
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月21日 下午4:04:18
 */
public abstract class ExceptionUtil {

  /**
   * 将异常转换成<code>RuntimeException</code>。
   * 
   * @param exception 受检异常
   * @return to <code>RuntimeException</code
   */
  public static RuntimeException toRuntimeException(Throwable exception) {
    return toRuntimeException(exception, null);
  }

  /**
   * 将异常转换成<code>RuntimeException</code>。
   * 
   * @param message 异常信息
   * @return to <code>RuntimeException</code
   */
  public static RuntimeException toRuntimeException(String message) {
    return new RuntimeException(message);
  }

  /**
   * 将异常转换成<code>RuntimeException</code>。
   * 
   * @param exception 受检异常
   * @param runtimeExceptionClass 转换异常的类型
   * @return to <code>RuntimeException</code
   */
  public static RuntimeException toRuntimeException(Throwable exception,
      Class<? extends RuntimeException> runtimeExceptionClass) {
    if (exception == null) {
      return null;
    }

    if (exception instanceof RuntimeException) {
      return (RuntimeException) exception;
    }
    if (runtimeExceptionClass == null) {
      return new RuntimeException(exception);
    }

    RuntimeException runtimeException;

    try {
      runtimeException = runtimeExceptionClass.newInstance();
    } catch (Exception ee) {
      return new RuntimeException(exception);
    }

    runtimeException.initCause(exception);
    return runtimeException;

  }

  /**
   * 抛出Throwable，但不需要声明<code>throws Throwable</code>，区分<code>Exception</code> 或</code>Error</code>。
   * 
   * @param throwable 受检异常
   * @throws Exception
   */
  public static void throwExceptionOrError(Throwable throwable) throws Exception {
    if (throwable instanceof Exception) {
      throw (Exception) throwable;
    }

    if (throwable instanceof Error) {
      throw (Error) throwable;
    }

    throw new RuntimeException(throwable); // unreachable code
  }

  /**
   * 抛出Throwable，但不需要声明<code>throws Throwable</code>，区分
   * <code>RuntimeException</code>、<code>Exception</code> 或</code>Error</code>。
   * 
   * @param throwable 受检异常
   */
  public static void throwRuntimeExceptionOrError(Throwable throwable) {
    if (throwable instanceof Error) {
      throw (Error) throwable;
    }

    if (throwable instanceof RuntimeException) {
      throw (RuntimeException) throwable;
    }

    throw new RuntimeException(throwable);
  }

  /**
   * 取得异常的stacktrace字符串。
   * 
   * @param throwable 受检异常
   * @return stacktrace字符串
   */
  public static String getStackTrace(Throwable throwable) {
    StringWriter buffer = new StringWriter();
    PrintWriter out = new PrintWriter(buffer);

    throwable.printStackTrace(out);
    out.flush();

    return buffer.toString();
  }

}
