/**
 * 
 */
package com.sm.common.libs.able;

/**
 * 定义返回值接口
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月30日 下午3:10:15
 * @param <T>
 */
public interface Valuable<T> {

  /**
   * 取值
   * 
   * @return the value
   */
  T value();
}
