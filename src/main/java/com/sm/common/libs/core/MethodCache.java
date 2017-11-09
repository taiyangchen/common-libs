/**
 * 
 */
package com.sm.common.libs.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.ArrayUtils;

import com.sm.common.libs.able.Computable;
import com.sm.common.libs.util.ClassUtil;
import com.sm.common.libs.util.MethodUtil;

/**
 * 用于缓存反射出来的<code>Method</code>，避免重复多次反射，提高性能
 * 
 * @author <a href="mailto:xuchen06@baidu.com">xuc</a>
 * @version create on 2014年8月1日 下午12:32:21
 */
public class MethodCache {

  /**
   * 搞个单例避免产生重复数据
   */
  private static final MethodCache INSTANCE = new MethodCache();

  /**
   * 用于缓存<code>Method</code>数组 @see ConcurrentCache
   */
  private final Computable<String, Method[]> cache = ConcurrentCache.createComputable();

  /**
   * 用于缓存单个<code>Method</code> @see ConcurrentCache
   */
  private final Computable<String, Map<String, Method>> cachedMap = ConcurrentCache.createComputable();

  private MethodCache() {

  }

  public static MethodCache getInstance() {
    return INSTANCE;
  }

  /**
   * 生成并缓存<code>Class</code>的所有<code>Method</code>
   * 
   * @param clazz 目标class
   * @return <code>Class</code>的所有public的<code>Method</code>
   */
  public Method[] getPublicMethods(final Class<?> clazz) {
    if (clazz == null) {
      return null;
    }

    return cache.get(ClassUtil.getFriendlyClassName(clazz) + ".public", new Callable<Method[]>() {
      @Override
      public Method[] call() throws Exception {
        return clazz.getMethods();
      }
    });
  }

  /**
   * 生成并缓存<code>Class</code>的所有<code>Method</code>
   * 
   * @param clazz 目标class
   * @return <code>Class</code>的所有<code>Method</code>
   */
  public Method[] getMethods(final Class<?> clazz) {
    if (clazz == null) {
      return null;
    }

    return cache.get(ClassUtil.getFriendlyClassName(clazz), new Callable<Method[]>() {
      @Override
      public Method[] call() throws Exception {
        return MethodUtil.getAllMethodsOfClass(clazz, true);
      }
    });

  }

  /**
   * 生成并缓存<code>Class</code>的所有带<code>annotationClass</code>的 <code>Method</code>
   * 
   * @param clazz 目标class
   * @param annotationClass 指定注解
   * @return 所有带<code>annotationClass</code>的 <code>Method</code>
   */
  public Method[] getMethods(final Class<?> clazz, final Class<? extends Annotation> annotationClass) {
    if (clazz == null || annotationClass == null) {
      return null;
    }

    return cache.get(ClassUtil.getFriendlyClassName(clazz) + "." + annotationClass.getName(), new Callable<Method[]>() {
      @Override
      public Method[] call() throws Exception {
        return MethodUtil.getAnnotationMethods(clazz, annotationClass).toArray(new Method[0]);
      }
    });
  }

  /**
   * 生成并缓存<code>Class</code>的所有实例<code>Method</code>
   * 
   * @param clazz 目标class
   * @return <code>Class</code>的所有实例<code>Method</code>
   */
  public Method[] getInstanceMethods(final Class<?> clazz) {
    if (clazz == null) {
      return null;
    }

    return cache.get(ClassUtil.getFriendlyClassName(clazz) + ".instance", new Callable<Method[]>() {
      @Override
      public Method[] call() throws Exception {
        return MethodUtil.getAllInstanceMethods(clazz, true);
      }
    });
  }

  /**
   * 生成并缓存<code>Class</code>的所有实例的Public类型<code>Method</code>
   * <p>
   * FIXME
   * 
   * @param clazz 目标class
   * @return <code>Class</code>的所有实例的Public类型<code>Method</code>
   */
  public Method[] getInstancePublicMethods(final Class<?> clazz) {
    if (clazz == null) {
      return null;
    }

    return cache.get(ClassUtil.getFriendlyClassName(clazz) + ".instance.public", new Callable<Method[]>() {
      @Override
      public Method[] call() throws Exception {
        List<Method> list = new ArrayList<>();
        Method[] methods = MethodUtil.getAllInstanceMethods(clazz, true);
        for (Method method : methods) {
          if (Modifier.isPublic(method.getModifiers())) {
            list.add(method);
          }
        }
        return list.toArray(new Method[0]);
      }
    });
  }

  /**
   * 获取方法
   * 
   * @param clazz 目标class
   * @param methodName 方法名
   * @param params 方法参数集
   * @return 方法<code>Method</code>
   */
  public Method getMethod(final Class<?> clazz, String methodName, Class<?>... params) {
    if (clazz == null) {
      return null;
    }

    Map<String, Method> map = cachedMap.get(ClassUtil.getFriendlyClassName(clazz), new Callable<Map<String, Method>>() {
      @Override
      public Map<String, Method> call() throws Exception {
        Method[] methods = getMethods(clazz);
        Map<String, Method> methodMap = new HashMap<>(methods.length);

        for (Method method : methods) {
          Class<?>[] types = method.getParameterTypes();
          String name = getMethodSignature(method.getName(), types);
          methodMap.put(name, method);
        }

        return methodMap;
      }
    });

    return map.get(getMethodSignature(methodName, params));
  }

  /**
   * 根据注解类型获取方法
   * 
   * @param clazz 目标class
   * @param annotationClass 注解类型
   * @param methodName 方法名
   * @param params 方法参数集
   * @return 方法<code>Method</code>
   */
  public Method getMethod(final Class<?> clazz, final Class<? extends Annotation> annotationClass, String methodName,
      Class<?>... params) {
    if (clazz == null) {
      return null;
    }

    Map<String, Method> map = cachedMap.get(ClassUtil.getFriendlyClassName(clazz) + "." + annotationClass.getName(),
        new Callable<Map<String, Method>>() {
          @Override
          public Map<String, Method> call() throws Exception {
            Method[] methods = getMethods(clazz, annotationClass);
            Map<String, Method> methodMap = new HashMap<>(methods.length);

            for (Method method : methods) {
              Class<?>[] types = method.getParameterTypes();
              String name = getMethodSignature(method.getName(), types);
              methodMap.put(name, method);
            }

            return methodMap;
          }
        });

    return map.get(getMethodSignature(methodName, params));
  }

  /**
   * 获取实例方法
   * 
   * @param clazz 目标class
   * @param methodName 方法名
   * @param params 方法参数集
   * @return 方法<code>Method</code>
   */
  public Method getInstanceMethod(final Class<?> clazz, String methodName, Class<?>... params) {
    if (clazz == null) {
      return null;
    }

    Map<String, Method> map =
        cachedMap.get(ClassUtil.getFriendlyClassName(clazz) + ".instance", new Callable<Map<String, Method>>() {
          @Override
          public Map<String, Method> call() throws Exception {
            Method[] methods = getInstanceMethods(clazz);
            Map<String, Method> methodMap = new HashMap<>(methods.length);

            for (Method method : methods) {
              Class<?>[] types = method.getParameterTypes();
              String name = getMethodSignature(method.getName(), types);
              methodMap.put(name, method);
            }

            return methodMap;
          }
        });

    return map.get(getMethodSignature(methodName, params));
  }

  /**
   * 获取方法签名
   * 
   * @param methodName 方法名
   * @param params 方法参数集
   * @return 方法签名
   */
  private static String getMethodSignature(String methodName, Class<?>... params) {
    String name = methodName + (ArrayUtils.isEmpty(params) ? "" : ArrayUtils.toString(params));

    return name;
  }

}
