/**
 * 
 */
package com.sm.common.libs.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 有关<code>Object</code>处理的工具类。
 * 
 * <p>
 * 这个类中的每个方法都可以“安全”地处理<code>null</code>，而不会抛出<code>NullPointerException</code>。
 * </p>
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年12月17日 下午7:28:50
 */
public abstract class ObjectUtil {

  /**
   * 判断对象是否均为<code>null</code>
   * 
   * @param objects 传入对象集
   * @return 均为<code>null</code>则返还<code>true</code>
   */
  public static boolean isAllNull(Object... objects) {
    if (objects == null) {
      return true;
    }

    for (Object object : objects) {
      if (object != null) {
        return false;
      }
    }
    return true;
  }

  /**
   * 判断是否有任一对象为<code>null</code>
   * 
   * @param objects 传入对象集
   * @return 任一对象为<code>null</code>则返还<code>true</code>
   */
  public static boolean isAnyNull(Object... objects) {
    if (objects == null) {
      return true;
    }

    for (Object object : objects) {
      if (object == null) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判断对象是否是空
   * 
   * @param object 传入对象
   * 
   * @return 如果<code>object.toString()</code>为<code>""</code>、 <code>null</code> 或者空数组,
   *         则返回<code>true</code>
   */
  public static boolean isEmpty(Object object) {
    if (object == null) {
      return true;
    }

    if (object instanceof String) {
      return StringUtils.isEmpty(object.toString());
    }

    if (object instanceof Collection) {
      return ((Collection<?>) object).size() == 0;
    }

    if (object instanceof Map) {
      return ((Map<?, ?>) object).size() == 0;
    }
    if (object.getClass().isArray()) {
      return Array.getLength(object) == 0;
    }

    return false;
  }

  // ==========================================================================
  // 比较函数。
  //
  // 以下方法用来比较两个对象的值或类型是否相同。
  // ==========================================================================

  /**
   * 比较两个对象是否完全相等。
   * <p>
   * 此方法可以正确地比较多维数组。
   * <p/>
   * 
   * <pre>
   * ObjectUtil.equals(null, null)                  = true
   * ObjectUtil.equals(null, "")                    = false
   * ObjectUtil.equals("", null)                    = false
   * ObjectUtil.equals("", "")                      = true
   * ObjectUtil.equals(Boolean.TRUE, null)          = false
   * ObjectUtil.equals(Boolean.TRUE, "true")        = false
   * ObjectUtil.equals(Boolean.TRUE, Boolean.TRUE)  = true
   * ObjectUtil.equals(Boolean.TRUE, Boolean.FALSE) = false
   * </pre>
   * <p/>
   * </p>
   * 
   * @param object1 对象1
   * @param object2 对象2
   * @return 如果相等, 则返回<code>true</code>
   */
  public static boolean isEquals(Object object1, Object object2) {
    if (object1 == object2) {
      return true;
    }

    if (object1 == null || object2 == null) {
      return false;
    }

    if (!object1.getClass().equals(object2.getClass())) {
      return false;
    }

    if (object1 instanceof Object[]) {
      return Arrays.deepEquals((Object[]) object1, (Object[]) object2);
    }
    if (object1 instanceof int[]) {
      return Arrays.equals((int[]) object1, (int[]) object2);
    }
    if (object1 instanceof long[]) {
      return Arrays.equals((long[]) object1, (long[]) object2);
    }
    if (object1 instanceof short[]) {
      return Arrays.equals((short[]) object1, (short[]) object2);
    }
    if (object1 instanceof byte[]) {
      return Arrays.equals((byte[]) object1, (byte[]) object2);
    }
    if (object1 instanceof double[]) {
      return Arrays.equals((double[]) object1, (double[]) object2);
    }
    if (object1 instanceof float[]) {
      return Arrays.equals((float[]) object1, (float[]) object2);
    }
    if (object1 instanceof char[]) {
      return Arrays.equals((char[]) object1, (char[]) object2);
    }
    if (object1 instanceof boolean[]) {
      return Arrays.equals((boolean[]) object1, (boolean[]) object2);
    }
    return object1.equals(object2);
  }


}
