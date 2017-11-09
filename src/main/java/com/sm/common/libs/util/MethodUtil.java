/**
 * 
 */
package com.sm.common.libs.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <code>Method</code>相关的方法。
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月22日 下午2:06:07
 */
public abstract class MethodUtil {

  /**
   * 获取类的所有<code>Method</code>，不包括<code>java.lang.Object</code>的 <code>Method</code>
   * <p>
   * 如果<code>clazz</code>为<code>null</code>，返回<code>null</code>
   * 
   * 
   * @param clazz 要获取的类
   * @return <code>Method</code>数组
   */
  public static Method[] getAllMethodsOfClass(final Class<?> clazz) {
    if (clazz == null) {
      return null;
    }
    Method[] methods = null;
    for (Class<?> itr = clazz; ClassUtil.hasSuperClass(itr);) {
      methods = ArrayUtils.addAll(itr.getDeclaredMethods(), methods);
      itr = itr.getSuperclass();
    }
    return methods;
  }

  /**
   * 获取类的所有<code>Method</code>，不包括<code>java.lang.Object</code>的 <code>Method</code>
   * <p>
   * 如果<code>clazz</code>为<code>null</code>，返回<code>null</code>
   * 
   * @param clazz 要获取的类
   * @param accessible 是否允许访问
   * @return <code>Method</code>数组
   */
  public static Method[] getAllMethodsOfClass(Class<?> clazz, boolean accessible) {
    Method[] methods = getAllMethodsOfClass(clazz);
    if (ArrayUtils.isNotEmpty(methods)) {
      AccessibleObject.setAccessible(methods, accessible);
    }

    return methods;
  }

  /**
   * 获取类的所有实例<code>Method</code>，不包括<code>java.lang.Object</code>的 <code>Method</code>
   * <p>
   * 如果<code>clazz</code>为<code>null</code>，返回<code>null</code>
   * 
   * 
   * @param clazz 要获取的类
   * @return <code>Method</code>数组
   */
  public static Method[] getAllInstanceMethods(final Class<?> clazz) {
    if (clazz == null) {
      return null;
    }

    List<Method> methods = new ArrayList<>();
    for (Class<?> itr = clazz; ClassUtil.hasSuperClass(itr);) {
      for (Method method : itr.getDeclaredMethods()) {
        if (!Modifier.isStatic(method.getModifiers())) {
          methods.add(method);
        }
      }
      itr = itr.getSuperclass();
    }

    return methods.toArray(new Method[methods.size()]);

  }

  /**
   * 获取类的所有实例<code>Method</code>，不包括<code>java.lang.Object</code>的 <code>Method</code>
   * <p>
   * 如果<code>clazz</code>为<code>null</code>，返回<code>null</code>
   * 
   * @param clazz 要获取的类
   * @param accessible 是否允许访问
   * @return <code>Method</code>数组
   */
  public static Method[] getAllInstanceMethods(Class<?> clazz, boolean accessible) {
    Method[] methods = getAllInstanceMethods(clazz);
    if (ArrayUtils.isNotEmpty(methods)) {
      AccessibleObject.setAccessible(methods, accessible);
    }

    return methods;
  }

  /**
   * 获取<code>clazz</code>下所有带指定<code>Annotation</code>的<code>Method</code>集合。
   * <p>
   * 如果 <code>clazz</code>或<code>annotationType</code>为<code>null</code> ，则返还 <code>null</code>
   * 
   * @param clazz 要获取的类
   * @param annotationType 指定的<code>Annotation</code>
   * @return <code>clazz</code>下所有带指定<code>Annotation</code>的 <code>Method</code>集合
   */
  public static <T, A extends Annotation> List<Method> getAnnotationMethods(Class<T> clazz, Class<A> annotationType) {
    if (clazz == null || annotationType == null) {
      return null;
    }
    List<Method> list = new ArrayList<>();

    for (Method method : getAllMethodsOfClass(clazz)) {
      A type = method.getAnnotation(annotationType);
      if (type != null) {
        list.add(method);
      }
    }

    return list;
  }

  /**
   * 判断方法返回是否为<code>Void</code>类型
   * 
   * @param method 指定方法
   * @return 方法返回是否为<code>Void</code>类型
   */
  public static boolean isReturnVoid(Method method) {
    if (method == null) {
      return false;
    }

    // FIXME
    Class<?> returnClass = method.getReturnType();
    return returnClass == void.class || returnClass == Void.class;
  }

  /**
   * 方法调用，如果<code>clazz</code>为<code>null</code>，返回<code>null</code>；
   * <p>
   * 如果<code>method</code>为<code>null</code>，返回<code>null</code>
   * <p>
   * 如果<code>target</code>为<code>null</code>，则为静态方法
   * 
   * @param method 调用的方法
   * @param target 目标对象
   * @param args 方法的参数值
   * @return 调用结果
   */
  public static <T> T invokeMethod(Method method, Object target, Object... args) {
    if (method == null) {
      return null;
    }

    method.setAccessible(true);
    try {
      @SuppressWarnings("unchecked")
      T result = (T) method.invoke(target, args);

      return result;
    } catch (Exception ex) {
      throw ExceptionUtil.toRuntimeException(ex);
    }

  }

  /**
   * 方法调用，如果<code>clazz</code>为<code>null</code>，返回<code>null</code>；
   * <p>
   * 如果<code>method</code>为<code>null</code>，返回<code>null</code>
   * <p>
   * 如果<code>target</code>为<code>null</code>，则为静态方法
   * 
   * @param method 调用的方法
   * @param target 目标对象
   * @return 调用结果
   */
  public static <T> T invokeMethod(Method method, Object target) {
    if (method == null) {
      return null;
    }

    method.setAccessible(true);
    try {
      @SuppressWarnings("unchecked")
      T result = (T) method.invoke(target, new Object[0]);

      return result;
    } catch (Exception ex) {
      throw ExceptionUtil.toRuntimeException(ex);
    }

  }

  /**
   * 调用一个命名的方法，其参数类型相匹配的对象类型。
   * 
   * @param object 调用方法作用的对象
   * @param methodName 方法名
   * @param parameterTypes 参数类型
   * @param args 参数值
   * @return 调用的方法的返回值
   */
  public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object... args) {
    if (object == null || StringUtils.isEmpty(methodName)) {
      return null;
    }

    if (parameterTypes == null) {
      parameterTypes = ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    if (args == null) {
      args = ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    Method method;
    try {
      method = object.getClass().getDeclaredMethod(methodName, parameterTypes);
    } catch (Exception ex) {
      throw ExceptionUtil.toRuntimeException(ex);
    }
    if (method == null) {
      return null;
    }

    return invokeMethod(method, object, args);

  }

  /**
   * 调用一个命名的方法，其参数类型相匹配的对象类型。
   * 
   * @param object 调用方法作用的对象
   * @param methodName 方法名
   * @param args 参数值
   * @return 调用的方法的返回值
   */
  public static Object invokeMethod(Object object, String methodName, Object... args) {
    if (object == null || StringUtils.isEmpty(methodName)) {
      return null;
    }
    if (args == null) {
      args = ArrayUtils.EMPTY_OBJECT_ARRAY;
    }

    int arguments = args.length;
    Class<?>[] parameterTypes = new Class[arguments];
    for (int i = 0; i < arguments; i++) {
      parameterTypes[i] = args[i].getClass();
    }

    return invokeMethod(object, methodName, parameterTypes, args);
  }

  public static <T> T invokeStaticMethod(Class<?> clazz, String methodName) {
    Method method;
    try {
      method = clazz.getDeclaredMethod(methodName, ArrayUtils.EMPTY_CLASS_ARRAY);
    } catch (Exception ex) {
      throw ExceptionUtil.toRuntimeException(ex);
    }
    if (method == null) {
      return null;
    }

    return invokeMethod(method, (Object) null, ArrayUtils.EMPTY_OBJECT_ARRAY);
  }

  /**
   * <p>
   * 调用一个命名的静态方法，其参数类型相匹配的对象类型。
   * </p>
   * 
   * 
   * @param clazz 调用方法作用的类
   * @param methodName 方法名
   * @param parameterTypes 参数类型
   * @param args 参数值
   * @return 调用的方法的返回值
   * 
   */
  public static <T> T invokeStaticMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object... args) {
    if (parameterTypes == null) {
      parameterTypes = ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    if (args == null) {
      args = ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    Method method;
    try {
      method = clazz.getDeclaredMethod(methodName, parameterTypes);
    } catch (Exception ex) {
      throw ExceptionUtil.toRuntimeException(ex);
    }
    if (method == null) {
      return null;
    }

    return invokeMethod(method, (Object) null, args);
  }

  public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
    if (clazz == null || StringUtils.isBlank(methodName)) {
      return null;
    }

    for (Class<?> itr = clazz; ClassUtil.hasSuperClass(itr);) {
      Method[] methods = itr.getDeclaredMethods();

      for (Method method : methods) {
        if (method.getName().equals(methodName) && Arrays.equals(method.getParameterTypes(), parameterTypes)) {
          return method;
        }
      }

      itr = itr.getSuperclass();
    }

    return null;

  }

  public static Method getMethod(Object object, String methodName, Class<?>... parameterTypes) {
    if (object == null || StringUtils.isBlank(methodName)) {
      return null;
    }

    for (Class<?> itr = object.getClass(); ClassUtil.hasSuperClass(itr);) {
      Method[] methods = itr.getDeclaredMethods();

      for (Method method : methods) {
        if (method.getName().equals(methodName) && Arrays.equals(method.getParameterTypes(), parameterTypes)) {
          return method;
        }
      }

      itr = itr.getSuperclass();
    }

    return null;

  }

}
