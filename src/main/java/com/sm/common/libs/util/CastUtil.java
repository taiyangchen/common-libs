package com.sm.common.libs.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.sm.common.libs.collection.ListMap;

/**
 * 泛型转换的工具类
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月22日 上午10:47:41
 */
public abstract class CastUtil {

  /**
   * 将一个对象进行类型转换
   * 
   * @param object 要转换的对象
   * @return 目标类型
   */
  public static <O> O cast(Object object) {
    @SuppressWarnings("unchecked")
    O result = (O) object;

    return result;
  }

  /**
   * 将一个数组转换成字符串数组
   * 
   * @param ts 要转换的数组
   * @return 字符串数组
   */
  public static <T> String[] cast(@SuppressWarnings("unchecked") T... ts) {
    if (ArrayUtils.isEmpty(ts)) {
      return null;
    }

    String[] result = new String[ts.length];
    for (int i = 0, size = ts.length; i < size; i++) {
      result[i] = String.valueOf(ts[i]);
    }

    return result;
  }

  /**
   * 将一个集合转换成字符串数组
   * 
   * @param ts 要转换的集合
   * @return 字符串数组
   */
  public static <T> String[] cast(Collection<T> ts) {
    if (CollectionUtil.isEmpty(ts)) {
      return null;
    }

    String[] result = new String[ts.size()];

    int i = 0;
    for (Iterator<T> iter = ts.iterator(); iter.hasNext(); i++) {
      T next = iter.next();
      result[i] = String.valueOf(next);
    }

    return result;
  }

  /**
   * 将一个对象列表进行类型转换
   * 
   * @param values 要转换的对象列表
   * @return 目标类型的列表
   */
  public static <O> List<O> cast(List<Object> values) {
    if (CollectionUtil.isEmpty(values)) {
      return null;
    }

    @SuppressWarnings("unchecked")
    List<O> result = (List<O>) values;
    return result;
  }

  /**
   * 将一个类型列表转换成对象列表
   * 
   * @param values 要转换的列表
   * @return 对象列表
   */
  public static <O> List<Object> castList(List<O> values) {
    if (CollectionUtil.isEmpty(values)) {
      return null;
    }

    @SuppressWarnings("unchecked")
    List<Object> result = (List<Object>) values;
    return result;
  }

  /**
   * 将一个泛型列表进行类型转换
   * 
   * @param values 要转换的泛型列表
   * @return 目标类型的列表
   */
  public static <O> List<O> castWildcard(List<?> values) {
    if (CollectionUtil.isEmpty(values)) {
      return null;
    }

    @SuppressWarnings("unchecked")
    List<O> result = (List<O>) values;
    return result;
  }

  /**
   * 将一个泛型map进行类型转换
   * 
   * @param map 要转换的泛型map
   * @return 目标类型的map
   */
  public static <K, V> Map<K, V> castMap(Map<?, ?> map) {
    if (CollectionUtil.isEmpty(map)) {
      return null;
    }

    @SuppressWarnings("unchecked")
    Map<K, V> result = (Map<K, V>) map;
    return result;
  }

  public static <K, V> ListMap<K, V> castListMap(ListMap<?, ?> map) {
    if (CollectionUtil.isEmpty(map)) {
      return null;
    }

    @SuppressWarnings("unchecked")
    ListMap<K, V> result = (ListMap<K, V>) map;
    return result;
  }

}
