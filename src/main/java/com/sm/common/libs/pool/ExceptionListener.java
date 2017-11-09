package com.sm.common.libs.pool;

import java.util.List;

/**
 * 异常监听器
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月16日 下午5:32:06
 * @param <T>
 */
public interface ExceptionListener<T> {

  /**
   * 异常处理
   * 
   * @param records 记录列表
   */
  void onException(List<T> records);

}
