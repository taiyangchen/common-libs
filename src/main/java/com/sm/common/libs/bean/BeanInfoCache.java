/**
 * 
 */
package com.sm.common.libs.bean;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.sm.common.libs.able.Computable;
import com.sm.common.libs.core.ConcurrentCache;
import com.sm.common.libs.core.LoggerSupport;
import com.sm.common.libs.util.ExceptionUtil;

/**
 * Bean信息缓存
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年12月17日 下午7:27:48
 */
public class BeanInfoCache extends LoggerSupport {

  /**
   * 单例
   */
  private static final BeanInfoCache instance = new BeanInfoCache();

  /**
   * 属性计算缓存 @see ConcurrentCache
   */
  private final Computable<String, Map<String, PropertyDescriptor>> propertyCached = ConcurrentCache.createComputable();

  /**
   * 方法计算缓存 @see ConcurrentCache
   */
  private final Computable<String, Map<String, MethodDescriptor>> methodCached = ConcurrentCache.createComputable();

  private BeanInfoCache() {

  }

  public static BeanInfoCache getInstance() {
    return instance;
  }

  /**
   * 通过自省获取bean信息
   * 
   * @param beanClass bean类型
   * @return bean信息 @see BeanInfo
   */
  public BeanInfo getBeanInfo(Class<?> beanClass) {
    try {
      return Introspector.getBeanInfo(beanClass, Object.class);
    } catch (IntrospectionException e) {
      logger.error("Introspector.getBeanInfo {} error", e, beanClass);
      throw ExceptionUtil.toRuntimeException(e);
    }
  }

  /**
   * 通过自省获取bean信息
   * 
   * @param beanClass bean类型
   * @param 停止加载的父类型
   * @return bean信息 @see BeanInfo
   */
  public BeanInfo getBeanInfo(Class<?> beanClass, Class<?> stopClass) {
    try {
      return Introspector.getBeanInfo(beanClass, stopClass);
    } catch (IntrospectionException e) {
      logger.error("Introspector.getBeanInfo {} error", e, beanClass);
      throw ExceptionUtil.toRuntimeException(e);
    }
  }

  /**
   * 获取bean描述符
   * 
   * @param beanClass bean类型
   * @return bean描述符 @see BeanDescriptor
   */
  public BeanDescriptor getBeanDescriptor(Class<?> beanClass) {
    BeanInfo beanInfo = getBeanInfo(beanClass);

    return beanInfo.getBeanDescriptor();
  }

  /**
   * 获取bean属性描述符映射表
   * 
   * @param beanClass bean类型
   * @param 停止加载的父类型
   * @return 获取bean属性描述符映射表 @see PropertyDescriptor
   */
  private Map<String, PropertyDescriptor> getPropertyMap(final Class<?> beanClass, final Class<?> stopClass) {
    String key = (stopClass == null) ? beanClass.getName() : beanClass.getName() + "_" + stopClass.getName();

    return propertyCached.get(key, new Callable<Map<String, PropertyDescriptor>>() {
      @Override
      public Map<String, PropertyDescriptor> call() throws Exception {
        BeanInfo beanInfo = getBeanInfo(beanClass, stopClass);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        Map<String, PropertyDescriptor> map = new HashMap<>(propertyDescriptors.length);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
          map.put(propertyDescriptor.getName(), propertyDescriptor);
        }

        return map;
      }
    });
  }

  /**
   * 获取bean属性描述符映射表
   * 
   * @param beanClass bean类型
   * @return bean属性描述符映射表 @see PropertyDescriptor
   */
  public Map<String, PropertyDescriptor> getPropertyDescriptor(final Class<?> beanClass) {
    Map<String, PropertyDescriptor> map = getPropertyMap(beanClass, null);

    return Collections.unmodifiableMap(map);
  }

  /**
   * 获取bean属性描述符映射表
   * 
   * @param beanClass bean类型
   * @param 停止加载的父类型
   * @return bean属性描述符映射表 @see PropertyDescriptor
   */
  public Map<String, PropertyDescriptor> getPropertyDescriptor(final Class<?> beanClass, final Class<?> stopClass) {
    Map<String, PropertyDescriptor> map = getPropertyMap(beanClass, stopClass);

    return Collections.unmodifiableMap(map);
  }

  /**
   * 获取属性描述符
   * 
   * @param beanClass bean类型
   * @param propertyName 属性名称
   * @return 属性描述符 @see PropertyDescriptor
   */
  public PropertyDescriptor getPropertyDescriptor(final Class<?> beanClass, String propertyName) {
    Map<String, PropertyDescriptor> map = getPropertyMap(beanClass, null);
    return map.get(propertyName);
  }

  /**
   * 获取bean方法描述符映射表
   * 
   * @param beanClass bean类型
   * @param 停止加载的父类型
   * @return bean方法描述符映射表 @see MethodDescriptor
   */
  private Map<String, MethodDescriptor> getMethodMap(final Class<?> beanClass, final Class<?> stopClass) {
    String key = (stopClass == null) ? beanClass.getName() : beanClass.getName() + "_" + stopClass.getName();

    return methodCached.get(key, new Callable<Map<String, MethodDescriptor>>() {
      @Override
      public Map<String, MethodDescriptor> call() throws Exception {
        BeanInfo beanInfo = getBeanInfo(beanClass, stopClass);
        MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
        Map<String, MethodDescriptor> map = new HashMap<>(methodDescriptors.length);
        for (MethodDescriptor methodDescriptor : methodDescriptors) {
          map.put(methodDescriptor.getName(), methodDescriptor);
        }

        return map;
      }
    });
  }

  /**
   * 获取bean方法描述符映射表
   * 
   * @param beanClass bean类型
   * @param 停止加载的父类型
   * @return bean方法描述符映射表 @see MethodDescriptor
   */
  public Map<String, MethodDescriptor> getMethodDescriptor(final Class<?> beanClass, final Class<?> stopClass) {
    Map<String, MethodDescriptor> map = getMethodMap(beanClass, stopClass);

    return Collections.unmodifiableMap(map);
  }

  /**
   * 获取bean方法描述符映射表
   * 
   * @param beanClass bean类型
   * @return bean方法描述符映射表 @see MethodDescriptor
   */
  public Map<String, MethodDescriptor> getMethodDescriptor(final Class<?> beanClass) {
    Map<String, MethodDescriptor> map = getMethodMap(beanClass, null);

    return Collections.unmodifiableMap(map);
  }

  /**
   * 获取bean方法描述符
   * 
   * @param beanClass bean类型
   * @param methodName 方法名
   * @return bean方法描述符 @see MethodDescriptor
   */
  public MethodDescriptor getMethodDescriptor(final Class<?> beanClass, String methodName) {
    Map<String, MethodDescriptor> map = getMethodMap(beanClass, null);

    return map.get(methodName);
  }

}
