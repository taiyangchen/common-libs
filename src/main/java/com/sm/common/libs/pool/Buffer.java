/**
 * 
 */
package com.sm.common.libs.pool;

import java.util.concurrent.TimeUnit;

import com.sm.common.libs.able.Bootstrap;

/**
 * 缓冲池，用于批处理
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月16日 下午5:33:03
 * @param <T>
 */
public interface Buffer<T> extends Bootstrap {

  /**
   * 名称
   * 
   * @return 名称
   */
  String getName();

  /**
   * 添加记录
   * 
   * @param record 记录
   * @return 是否成功
   */
  boolean add(T record);

  /**
   * 记录池中添加记录
   * 
   * @param record 记录
   * @param timeout 超时
   * @param unit 超时单位
   * @return 是否成功
   */
  boolean add(T record, long timeout, TimeUnit unit);

}
