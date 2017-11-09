/**
 * 
 */
package com.sm.common.libs.bean;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;

import com.sm.common.libs.able.Transformer;
import com.sm.common.libs.core.FieldCache;
import com.sm.common.libs.util.FieldUtil;
import com.sm.common.libs.util.MethodUtil;
import com.sm.common.libs.util.ObjectUtil;

/**
 * Bean拷贝工具类
 * 
 * @author <a href="mailto:xuchen06@baidu.com">xuc</a>
 * @version create on 2014年9月15日 下午4:09:04
 */
public abstract class BeanCopy {

  /**
   * <code>Field</code>缓存
   */
  protected static final FieldCache FIELD_CACHE = FieldCache.getInstance();

  /**
   * <code>BeanInfo</code>缓存
   */
  protected static final BeanInfoCache BEAN_INFO_CACHE = BeanInfoCache.getInstance();

  /**
   * 将原对象内容拷贝到目标对象
   * 
   * @param src 原对象
   * @param dest 目标对象
   * @param useAnnotation 是否使用注解
   */
  public static void copy(Object src, Object dest, boolean useAnnotation) {
    if (!useAnnotation) {
      copyProperties(src, dest);
      return;
    }

    copyByAnnotation(src, dest);
  }

  /**
   * 通过<code>Field</code>拷贝
   * 
   * @param src 原对象
   * @param dest 目标对象
   */
  public static void copyProperties(Object src, Object dest) {
    copyProperties(src, dest, false);
  }

  /**
   * 通过<code>Field</code>拷贝 FIXME just simple
   * 
   * @param src 原对象
   * @param dest 目标对象
   * @param transformer 转换器
   */
  public static void copyProperties(Object src, Object dest, Transformer<String, String> transformer) {
    if (ObjectUtil.isAnyNull(src, dest)) {
      return;
    }

    Class<?> srcClazz = src.getClass();
    Field[] destFields = FIELD_CACHE.getInstanceFields(dest.getClass());

    for (Field field : destFields) {
      if (FieldUtil.isFinal(field)) {
        continue;
      }
      Field srcField = FIELD_CACHE.getInstanceField(srcClazz, field.getName());
      if (srcField != null) {
        String descField = transformer.transform(srcField.getName());
        Object value = FieldUtil.readField(srcField, src);
        // Object target, String fieldName, Object value
        FieldUtil.writeField(dest, descField, value);
      }
    }
  }

  /**
   * 通过<code>Field</code>拷贝
   * 
   * @param src 原对象
   * @param dest 目标对象
   * @param ignoreNull 是否忽略<code>null</code>
   */
  public static void copyProperties(Object src, Object dest, boolean ignoreNull) {
    if (ObjectUtil.isAnyNull(src, dest)) {
      return;
    }

    Class<?> srcClazz = src.getClass();
    Field[] destFields = FIELD_CACHE.getInstanceFields(dest.getClass());

    for (Field field : destFields) {
      if (FieldUtil.isFinal(field)) {
        continue;
      }
      Field srcField = FIELD_CACHE.getInstanceField(srcClazz, field.getName());
      if (srcField != null) {
        Object value = FieldUtil.readField(srcField, src);
        if (value != null) {
          FieldUtil.writeField(field, dest, value);
          continue;
        }
        if (!ignoreNull) {
          FieldUtil.writeField(field, dest, value);
        }
      }
    }
  }

  /**
   * 通过注解拷贝
   * 
   * @param src 原对象
   * @param dest 目标对象
   */
  public static void copyByAnnotation(Object src, Object dest) {
    if (ObjectUtil.isAnyNull(src, dest)) {
      return;
    }

    Class<?> srcClazz = src.getClass();
    Class<?> destClazz = dest.getClass();
    Field[] destFields = FIELD_CACHE.getInstanceFields(dest.getClass());

    for (Field field : destFields) {
      if (FieldUtil.isFinal(field) || FieldUtil.hasAnnotation(field, IgnoreField.class)) {
        continue;
      }
      Field srcField = FIELD_CACHE.getField(srcClazz, CopyField.class, field.getName());
      if (srcField != null && supportFor(destClazz, srcField.getAnnotation(CopyField.class))) {
        Object value = FieldUtil.readField(srcField, src);
        FieldUtil.writeField(field, dest, value);
      }
    }
  }

  /**
   * 通过<code>Method</code>拷贝
   * 
   * @param src 原对象
   * @param dest 目标对象
   */
  public static void copyByMethod(Object src, Object dest) {
    if (ObjectUtil.isAnyNull(src, dest)) {
      return;
    }

    BeanInfo beanInfo = BEAN_INFO_CACHE.getBeanInfo(dest.getClass());

    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

    for (PropertyDescriptor destPd : propertyDescriptors) {
      PropertyDescriptor srcPd = BEAN_INFO_CACHE.getPropertyDescriptor(src.getClass(), destPd.getName());
      if (srcPd == null) {
        continue;
      }

      Method readMethod = srcPd.getReadMethod();
      Object value = MethodUtil.invokeMethod(readMethod, src);
      Method writeMethod = destPd.getWriteMethod();
      MethodUtil.invokeMethod(writeMethod, dest, value);

    }
  }

  private static boolean supportFor(Class<?> fromClass, CopyField copyField) {
    for (Class<?> clazz : copyField.supportFor()) {
      // ClassUtil.isAssignable(clazz, fromClass)
      if (ClassUtils.isAssignable(fromClass, clazz)) {
        return true;
      }
    }

    return false;
  }

  /**
   * 拷贝map到对象
   * 
   * @param object 对象
   * @param map 映射表
   */
  public static void map2Object(Object object, Map<?, ?> map) {
    Field[] fields = FieldUtil.getAllFieldsOfClass(object.getClass(), true);

    for (Field field : fields) {
      Object value = map.get(field.getName());
      if (value != null) {
        FieldUtil.writeField(object, field.getName(), value);
      }
    }
  }

  /**
   * 拷贝map到对象 FIXME by 2
   * 
   * @param object 对象
   * @param map 映射表
   * @param transformer 转换器
   */
  public static void map2Object(Object object, Map<?, ?> map, Transformer<String, String> transformer) {
    Field[] fields = FieldUtil.getAllFieldsOfClass(object.getClass(), true);

    for (Field field : fields) {
      String fieldName = field.getName();
      Object value = map.get(fieldName);
      if (value == null) {
        String transName = transformer.transform(fieldName);
        value = map.get(transName);
      }
      if (value != null) {
        FieldUtil.writeField(object, fieldName, value);
      }
    }
  }

  /**
   * 对象拷贝到map
   * 
   * @param object 对象
   * @param map 映射表
   */
  public static void object2Map(Object object, Map<String, Object> map) {
    Field[] fields = FieldUtil.getAllFieldsOfClass(object.getClass(), true);

    for (Field field : fields) {
      Object value = FieldUtil.readField(field, object);
      if (value != null) {
        map.put(field.getName(), value);
      }
    }
  }
}
