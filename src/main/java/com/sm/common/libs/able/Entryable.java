/**
 * 
 */
package com.sm.common.libs.able;

/**
 * 定义<code>Entry</code>，一般代表键值对
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年5月12日 上午3:31:55
 * @param <K>
 * @param <V>
 */
public interface Entryable<K, V> {

  /**
   * return the key of entry
   * 
   * @return key
   */
  K getKey();

  /**
   * return the value of entry
   * 
   * @return value
   */
  V getValue();

}
