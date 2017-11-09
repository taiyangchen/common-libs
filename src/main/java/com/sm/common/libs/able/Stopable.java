package com.sm.common.libs.able;

/**
 * 可“停止”的接口
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年12月1日 下午3:17:19
 */
public interface Stopable {
  /**
   * 停止
   * 
   * @throws Exception
   */
  void stop() throws Exception;
}
