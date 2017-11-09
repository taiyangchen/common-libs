/**
 * 
 */
package com.sm.common.libs.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 数字相关的工具类
 * <p>
 * 这个类中的每个方法都可以“安全”地处理<code>null</code>，而不会抛出<code>NullPointerException</code>。
 * </p>
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年5月15日 上午1:41:24
 */
public abstract class NumberUtil {
  /**
   * <p>
   * 将字符串split成整型数字，并以<code>List</code>类型返回，如果字符串为<code>null</code>则返回<code>null</code>。 <br>
   * 该方法不验证数字的正确性，如果分割后得到非数字类型，则会抛出<code>NumberFormatException</code>
   * </p>
   * 
   * @param nums 需要分割的字符串
   * @param separatorChars 分隔符
   * @return 分割成数字的<code>List</code>
   */
  public static List<Integer> split2IntList(String nums, String separatorChars) {
    String[] idArray = StringUtils.split(nums, separatorChars);
    if (ArrayUtils.isEmpty(idArray)) {
      return null;
    }

    List<Integer> result = new ArrayList<>(idArray.length);
    for (String id : idArray) {
      result.add(Integer.valueOf(id));
    }

    return result;
  }

  // =========================================================================================================
  // toPrimitive。
  // =========================================================================================================

  public static int toInt(Number wrapped) {
    if (wrapped == null) {
      return -1;
    }

    return wrapped.intValue();
  }

  /**
   * 判断是否都为0
   * 
   * @param longs 数字集
   * @return 是否都为0
   */
  public static boolean isAllZero(long... longs) {
    for (long number : longs) {
      if (number != 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * 判断是否有任意数字为0
   * 
   * @param longs 数字集
   * @return 任意数字为0
   */
  public static boolean isAnyZero(long... longs) {
    for (long number : longs) {
      if (number == 0) {
        return true;
      }
    }
    return false;
  }
}
