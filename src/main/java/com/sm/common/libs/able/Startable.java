package com.sm.common.libs.able;

/**
 * 可“启动”的接口
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年12月1日 下午3:17:07
 */
public interface Startable {

  /**
   * 启动
   * 
   * @throws Exception
   */
  void start() throws Exception;

}
