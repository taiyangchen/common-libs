package com.sm.common.libs.pool;

import java.util.List;

/**
 * 批处理执行器
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月16日 下午5:28:23
 * @param <T>
 */
public interface BatchExecutor<T> {

  /**
   * 执行操作
   * 
   * @param records 记录列表
   */
  void execute(List<T> records);

}
