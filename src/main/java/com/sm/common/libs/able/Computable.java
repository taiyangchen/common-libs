/**
 * 
 */
package com.sm.common.libs.able;

import java.util.concurrent.Callable;

/**
 * 计算接口
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月3日 上午10:59:14
 * @param <K>
 * @param <V>
 */
public interface Computable<K, V> {

  /**
   * 通过关键字来计算
   * 
   * @param key 查找关键字
   * @param callable # @see Callable
   * @return 计算结果
   */
  V get(K key, Callable<V> callable);

  /**
   * 通过关键字移除内容
   * 
   * @param key 查找关键字
   */
  void remove(K key);

  /**
   * 清空
   */
  void clear();

}
