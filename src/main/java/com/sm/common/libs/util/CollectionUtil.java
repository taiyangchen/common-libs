/**
 * 
 */
package com.sm.common.libs.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.sm.common.libs.able.Transformer;

/**
 * 集合类相关的工具类
 * 
 * <p>
 * 这个类中的每个方法都可以“安全”地处理 <code>null</code> ，而不会抛出 <code>NullPointerException</code>。
 * </p>
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年10月27日 上午1:15:10
 */
public abstract class CollectionUtil {

  /**
   * 判断<code>Map</code>是否为<code>null</code>或空<code>{}</code>
   * 
   * @param map ## @see Map
   * @return 如果为空, 则返回<code>true</code>
   */
  public static boolean isEmpty(Map<?, ?> map) {
    return (map == null) || (map.size() == 0);
  }

  /**
   * 判断<code>Collection</code>是否为<code>null</code>或空数组<code>[]</code>。
   * 
   * @param collection
   * @see Collection
   * @return 如果为空, 则返回<code>true</code>
   */
  public static boolean isEmpty(Collection<?> collection) {
    return (collection == null) || (collection.size() == 0);
  }

  /**
   * 判断Collection是否不为<code>null</code>和空数组<code>[]</code>。
   * 
   * @param collection
   * @return 如果不为空, 则返回<code>true</code>
   */
  public static boolean isNotEmpty(Collection<?> collection) {
    return (collection != null) && (collection.size() > 0);
  }

  /**
   * 判断Map是否不为<code>null</code>和空<code>{}</code>
   * 
   * @param map ## @see Map
   * @return 如果不为空, 则返回<code>true</code>
   */
  public static boolean isNotEmpty(Map<?, ?> map) {
    return (map != null) && (map.size() > 0);
  }

  /**
   * 创建<code>ArrayList</code>实例
   * 
   * @param V 构建对象集
   * @return <code>ArrayList</code>实例
   */
  public static <T, V extends T> ArrayList<T> createArrayList(V[] args) {
    if (args == null || args.length == 0) {
      return new ArrayList<T>();
    }

    ArrayList<T> list = new ArrayList<T>(args.length);

    for (V v : args) {
      list.add(v);
    }

    return list;
  }

  // ==========================================================================
  // Data Transformer and Filter。
  // ==========================================================================

  public static <T, E> void datasAppendToCollection(Collection<T> collection, Transformer<E, T> transformer,
      Collection<E> datas) {
    if (ObjectUtil.isAnyNull(collection, transformer) || isEmpty(datas)) {
      return;
    }

    for (E data : datas) {
      collection.add(transformer.transform(data));
    }
  }

  public static <T, E> void datasAppendToCollection(Collection<T> collection, Transformer<E, T> transformer,
      Iterable<E> datas) {
    if (ObjectUtil.isAnyNull(collection, transformer, datas)) {
      return;
    }

    for (E data : datas) {
      collection.add(transformer.transform(data));
    }
  }

  public static <T, E> void datasAppendToCollection(Collection<T> collection, Transformer<E, T> transformer,
      @SuppressWarnings("unchecked") E... datas) {
    if (ObjectUtil.isAnyNull(collection, transformer) || ArrayUtils.isEmpty(datas)) {
      return;
    }

    for (E data : datas) {
      collection.add(transformer.transform(data));
    }
  }

  public static <T, E> List<T> transformToList(Transformer<E, T> transformer, Collection<E> datas) {
    if (transformer == null || CollectionUtil.isEmpty(datas)) {
      return null;
    }

    List<T> list = new ArrayList<>(datas.size());

    for (E data : datas) {
      list.add(transformer.transform(data));
    }

    return list;
  }

  public static <T, E> List<T> transformToList(Transformer<E, T> transformer,
      @SuppressWarnings("unchecked") E... datas) {
    if (transformer == null || ArrayUtils.isEmpty(datas)) {
      return null;
    }

    List<T> list = new ArrayList<>(datas.length);

    for (E data : datas) {
      list.add(transformer.transform(data));
    }

    return list;
  }

}
