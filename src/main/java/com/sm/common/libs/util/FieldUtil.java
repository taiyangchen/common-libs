/**
 * 
 */
package com.sm.common.libs.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

/**
 * <code>Field</code>相关的方法。
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月22日 下午1:57:42
 */
public abstract class FieldUtil {

  /**
   * 要求参数不为<code>null</code>
   * <p>
   * 获取类及父类的所有非静态<code>Field</code>，不包括<code>Object</code>的 <code>Field</code>
   * 
   * @param clazz 要获取的类
   * @return <code>Field</code>数组
   */
  static Field[] getAllInstanceFields0(Class<?> clazz) {
    List<Field> fields = new ArrayList<>();
    for (Class<?> itr = clazz; itr != null;) {
      for (Field field : itr.getDeclaredFields()) {
        if (!Modifier.isStatic(field.getModifiers())) {
          fields.add(field);
        }
      }
      itr = itr.getSuperclass();
    }

    return fields.toArray(new Field[fields.size()]);
  }

  /**
   * 要求参数不为<code>null</code>
   * <p>
   * 获取类及父类的所有<code>Field</code>，不包括<code>Object</code>的 <code>Field</code>
   * 
   * @param clazz 要获取的类
   * @return <code>Field</code>数组
   */
  static Field[] getAllFieldsOfClass0(Class<?> clazz) {
    Field[] fields = null;

    for (Class<?> itr = clazz; itr != null;) {
      fields = ArrayUtils.addAll(itr.getDeclaredFields(), fields);
      itr = itr.getSuperclass();
    }

    return fields;
  }

  /**
   * 获取类及父类的所有<code>Field</code>，不包括<code>Object</code>的 <code>Field</code>
   * <p>
   * 如果<code>clazz</code>为<code>null</code>，返回<code>null</code>
   * 
   * @param clazz 要获取的类
   * @return <code>Field</code>数组
   */
  public static Field[] getAllFieldsOfClass(Class<?> clazz) {
    if (clazz == null) {
      return null;
    }

    return getAllFieldsOfClass0(clazz);
  }

  /**
   * 获取类及父类的所有<code>Field</code>，不包括<code>Object</code>的 <code>Field</code>
   * 
   * @param clazz 要获取的类
   * @param accessible 设置访问权限
   * @return <code>Field</code>数组
   */
  public static Field[] getAllFieldsOfClass(Class<?> clazz, boolean accessible) {
    Field[] fields = getAllFieldsOfClass(clazz);
    if (ArrayUtils.isNotEmpty(fields)) {
      AccessibleObject.setAccessible(fields, accessible);
    }

    return fields;
  }

  /**
   * 获取对象的所有<code>Field</code>，不包括<code>Object</code>的 <code>Field</code>
   * <p>
   * 如果<code>object</code>为<code>null</code>，返回<code>null</code>
   * 
   * @param object 要获取的对象
   * @return <code>Field</code>数组
   */
  public static Field[] getAllFieldsOfClass(Object object) {
    if (object == null) {
      return null;
    }

    Field[] fields = getAllFieldsOfClass0(object.getClass());
    return fields;
  }

  /**
   * 获取对象的所有<code>Field</code>，不包括<code>Object</code>的 <code>Field</code>*
   * <p>
   * 如果<code>object</code>为<code>null</code>，返回<code>null</code>
   * 
   * @param object 要获取的对象
   * @param accessible 设置访问权限
   * @return <code>Field</code>数组
   */
  public static Field[] getAllFieldsOfClass(Object object, boolean accessible) {
    if (object == null) {
      return null;
    }

    Field[] fields = getAllFieldsOfClass(object);
    AccessibleObject.setAccessible(fields, accessible);
    return fields;
  }

  /**
   * 获取类及父类的所有非静态<code>Field</code>，不包括<code>Object</code>的 <code>Field</code>
   * <p>
   * 如果<code>clazz</code>为<code>null</code>，返回<code>null</code>
   * 
   * @param clazz 要获取的类
   * @return <code>Field</code>数组
   */
  public static Field[] getAllInstanceFields(Class<?> clazz) {
    if (clazz == null) {
      return null;
    }

    return getAllInstanceFields0(clazz);
  }

  /**
   * 获取类及父类的所有非静态<code>Field</code>，不包括<code>Object</code>的 <code>Field</code>
   * 
   * @param clazz 要获取的类
   * @param accessible 设置访问权限
   * @return <code>Field</code>数组
   */
  public static Field[] getAllInstanceFields(Class<?> clazz, boolean accessible) {
    Field[] fields = getAllInstanceFields(clazz);
    if (ArrayUtils.isNotEmpty(fields)) {
      AccessibleObject.setAccessible(fields, accessible);
    }

    return fields;
  }

  /**
   * 获取对象的所有非静态<code>Field</code>，不包括<code>Object</code>的 <code>Field</code>
   * <p>
   * 如果<code>object</code>为<code>null</code>，返回<code>null</code>
   * 
   * @param object 要获取的对象
   * @return <code>Field</code>数组
   */
  public static Field[] getAllInstanceFields(Object object) {
    if (object == null) {
      return null;
    }

    Field[] fields = getAllInstanceFields0(object.getClass());
    return fields;
  }

  /**
   * 获取对象的所有非静态<code>Field</code>，不包括<code>Object</code>的 <code>Field</code>
   * <p>
   * 如果<code>object</code>为<code>null</code>，返回<code>null</code>
   * 
   * @param object 要获取的对象
   * @param accessible 设置访问权限
   * @return <code>Field</code>数组
   */
  public static Field[] getAllInstanceFields(Object object, boolean accessible) {
    if (object == null) {
      return null;
    }

    Field[] fields = getAllInstanceFields(object);
    AccessibleObject.setAccessible(fields, accessible);
    return fields;
  }

  /**
   * 获取所有包含指定<code>Annotation</code>的<code>Field</code>数组
   * 
   * @param clazz 查找类
   * @param annotationClass 注解类名
   * @return <code>Field</code>数组
   */
  public static Field[] getAnnotationFields(Class<?> clazz, Class<? extends Annotation> annotationClass) {
    if (clazz == null || annotationClass == null) {
      return null;
    }

    Field[] fields = getAllFieldsOfClass0(clazz);
    if (ArrayUtils.isEmpty(fields)) {
      return null;
    }

    List<Field> list = new ArrayList<>();
    for (Field field : fields) {
      if (null != field.getAnnotation(annotationClass)) {
        list.add(field);
        field.setAccessible(true);
      }
    }

    return list.toArray(new Field[0]);
  }

  /**
   * 读取<code>Field</code>的值
   * 
   * @param field 目标<code>Field</code>
   * @param target 目标对象
   * @return <code>Field</code>值
   */
  public static <T> T readField(Field field, Object target) {
    if (field == null || target == null) {
      return null;
    }

    try {
      @SuppressWarnings("unchecked")
      T result = (T) readField0(field, target);

      return result;
    } catch (Exception ex) {
      throw ExceptionUtil.toRuntimeException(ex);
    }
  }

  /**
   * 读取<code>Field</code>的值
   * 
   * @param fieldName 目标<code>Field</code>名
   * @param target 目标对象
   * @return <code>Field</code>值
   */
  public static Object readField(String fieldName, Object target) {
    Field field = getField(target, fieldName);
    if (field == null) {
      return null;
    }

    try {
      return readField0(field, target);
    } catch (Exception ex) {
      throw ExceptionUtil.toRuntimeException(ex);
    }
  }

  /**
   * 要求参数参数不为<code>null</code>
   * <p>
   * 读取<code>Field</code>的值
   * 
   * @param field 目标<code>Field</code>
   * @param target 目标对象
   * @return <code>Field</code>值
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   */
  static Object readField0(Field field, Object target) throws IllegalArgumentException, IllegalAccessException {
    if (!field.isAccessible()) {
      field.setAccessible(true);
    }

    return field.get(target);
  }

  /**
   * 写入<code>Field</code>的值
   * 
   * @param field 目标<code>Field</code>
   * @param target 目标对象
   * @param value 写入的值
   */
  public static void writeField(Field field, Object target, Object value) {
    if (ObjectUtil.isAllNull(field, target, value)) {
      return;
    }

    try {
      writeField0(field, target, value);
    } catch (Exception ex) {
      throw ExceptionUtil.toRuntimeException(ex);
    }
  }

  /**
   * 写入<code>Field</code>的值
   * 
   * @param target 目标对象
   * @param fieldName 目标<code>Field</code>名
   * @param value 写入的值
   */
  public static void writeField(Object target, String fieldName, Object value) {
    Field field = getField(target, fieldName);
    if (field != null) {
      try {
        writeField0(field, target, value);
      } catch (Exception ex) {
        throw ExceptionUtil.toRuntimeException(ex);
      }
    }

  }

  /**
   * 要求参数参数不为<code>null</code>
   * <p>
   * 写入<code>Field</code>的值
   * 
   * @param field 目标<code>Field</code>
   * @param target 目标对象
   * @param value 写入的值
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   */
  static void writeField0(Field field, Object target, Object value)
      throws IllegalArgumentException, IllegalAccessException {
    if (!field.isAccessible()) {
      field.setAccessible(true);
    }

    field.set(target, value);
  }

  /**
   * 读取静态<code>Field</code>的值
   * 
   * @param field 目标<code>Field</code>名
   * @return <code>Field</code>值
   */
  public static Object readStaticField(Field field) {
    if (field == null) {
      return null;
    }

    return readField(field, (Object) null);
  }

  /**
   * 读取静态<code>Field</code>的值
   * 
   * @param clazz 目标<code>Class</code>名
   * @return fieldName <code>Field</code>名
   * @throws FieldException
   */
  public static Object readStaticField(Class<?> clazz, String fieldName) {
    Field field = getField(clazz, fieldName);
    if (field == null) {
      return null;
    }

    return readStaticField(field);
  }

  /**
   * 写入<code>Field</code>的值
   * 
   * @param field 目标<code>Field</code>
   * @param value 写入的值
   * @throws FieldException
   */
  public static void writeStaticField(Field field, Object value) {
    if (field == null) {
      return;
    }

    writeField(field, (Object) null, value);
  }

  /**
   * 写入<code>Field</code>的值
   * 
   * @param clazz 目标<code>Class</code>
   * @return fieldName <code>Field</code>名
   * @param value 写入的值
   * @throws FieldException
   */
  public static void writeStaticField(Class<?> clazz, String fieldName, Object value) {
    Field field = getField(clazz, fieldName);
    if (field == null) {
      return;
    }

    writeStaticField(field, value);
  }

  /**
   * 根据对象的<code>Field</code>名返回<code>Field</code>
   * 
   * @param object 要获取的对象
   * @param fieldName <code>Field</code>名
   * @return <code>Field</code>
   */
  public static Field getField(Object object, String fieldName) {
    if (ObjectUtil.isAnyNull(object, fieldName)) {
      return null;
    }

    return getField0(object.getClass(), fieldName);
  }

  /**
   * 根据对象的<code>Field</code>名返回<code>Field</code>
   * 
   * @param clazz 要获取的类
   * @param fieldName <code>Field</code>名
   * @return <code>Field</code>
   */
  public static Field getField(Class<?> clazz, String fieldName) {
    if (ObjectUtil.isAnyNull(clazz, fieldName)) {
      return null;
    }

    return getField0(clazz, fieldName);
  }

  /**
   * 根据对象的<code>Field</code>名返回<code>Field</code>
   * 
   * @param object 要获取的对象
   * @param fieldName <code>Field</code>名
   * @param accessible 设置访问权限
   * @return <code>Field</code>
   */
  public static Field getField(Object object, String fieldName, boolean accessible) {
    Field field = getField(object, fieldName);
    if (field != null) {
      field.setAccessible(accessible);
    }

    return field;
  }

  /**
   * 根据对象的<code>Field</code>名返回<code>Field</code>
   * 
   * @param clazz 要获取的类
   * @param fieldName <code>Field</code>名
   * @param accessible 设置访问权限
   * @return <code>Field</code>
   */
  public static Field getField(Class<?> clazz, String fieldName, boolean accessible) {
    Field field = getField(clazz, fieldName);
    if (field != null) {
      field.setAccessible(accessible);
    }

    return field;
  }

  static Field getField0(Class<?> clazz, String fieldName) {
    // FIXME
    for (Class<?> itr = clazz; ClassUtil.hasSuperClass(itr);) {
      Field[] fields = itr.getDeclaredFields();
      for (Field field : fields) {
        if (field.getName().equals(fieldName)) {
          return field;
        }
      }

      itr = itr.getSuperclass();
    }

    return null;
  }

  public static <A extends Annotation> boolean hasAnnotation(Field field, Class<A> annotationType) {
    if (ObjectUtil.isAnyNull(field, annotationType)) {
      return false;
    }

    return field.getAnnotation(annotationType) != null;
  }
  
  public static boolean isFinal(Field field) {
    return field != null && Modifier.isFinal(field.getModifiers());
}

}
