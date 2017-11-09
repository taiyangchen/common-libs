/**
 * 
 */
package com.sm.common.libs.core;

import java.util.Comparator;

import com.sm.common.libs.able.Valuable;

/**
 * 根据枚举<code>Valuable</code>的code值大小进行排序
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年9月25日 下午3:46:06
 */
public class ValueComparator implements Comparator<Valuable<Integer>> {

  private static final Comparator<Valuable<Integer>> instance = new ValueComparator();

  public static Comparator<Valuable<Integer>> getInstance() {
    return instance;
  }

  @Override
  public int compare(Valuable<Integer> one, Valuable<Integer> another) {
    int result = one.value() - another.value();
    if (0 == result) {
      throw new RuntimeException(
          "value1:" + one.value() + "/value2:" + another.value() + " has the same order value, internal error.");
    }

    return result;
  }

}
