package com.sm.common.libs.able;

/**
 * Since {@link Cloneable} is just a marker interface, it is not possible to clone different type of
 * objects at once. This interface helps for user objects, but, obviously, it can't change JDK
 * classes.
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年12月1日 下午3:19:28
 * @param <T>
 */
public interface CloneableObject<T> extends Cloneable {

  /**
   * Performs instance cloning.
   * 
   * @see Object#clone()
   */
  T clone() throws CloneNotSupportedException;

}
