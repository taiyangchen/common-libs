/**
 * 
 */
package com.sm.common.libs.able;

/**
 * 具有主键性质的接口，类似<code>DB</code>表中的<code>id</code>
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月17日 下午1:44:18
 * @param <T>
 */
public interface Keyable<T> {

  /**
   * 返回ID
   * 
   * @return ID
   */
  public T getId();

}
