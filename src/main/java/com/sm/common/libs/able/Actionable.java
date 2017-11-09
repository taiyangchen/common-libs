/**
 * 
 */
package com.sm.common.libs.able;

/**
 * 操作接口
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年12月16日 上午11:02:56
 */
public interface Actionable<T, V> {

  /**
   * 具体操作
   * 
   * @param condition 操作条件
   */
  V action(T condition);


}
