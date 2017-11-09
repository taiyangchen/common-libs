package com.sm.common.libs.able;

/**
 * 接收信息处理
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年12月21日 上午2:01:06
 * @param <F>
 * @param <T>
 */
public interface DataHandler<F, T> {

  /**
   * 信息处理
   * 
   * @param from 原始信息
   * @param to 转换后信息
   */
  void handle(F from, T to);

}
