/**
 * 
 */
package com.sm.common.libs.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * AnnotationUtil
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年10月24日 上午1:05:59
 */
public abstract class AnnotationUtil {

  public static <A extends Annotation> boolean hasAnnotation(Method method, Class<A> annotationType) {
    if (ObjectUtil.isAnyNull(method, annotationType)) {
      return false;
    }

    return method.getAnnotation(annotationType) != null;
  }

  public static <A extends Annotation> boolean hasAnnotation(Field field, Class<A> annotationType) {
    if (ObjectUtil.isAnyNull(field, annotationType)) {
      return false;
    }

    return field.getAnnotation(annotationType) != null;
  }

  public static Annotation[] getAnnotation(AnnotatedElement annotatedElement) {
    if (ObjectUtil.isAnyNull(annotatedElement)) {
      return null;
    }

    return annotatedElement.getAnnotations();
  }

  public static <A extends Annotation> A getAnnotation(Class<?> clazz, Class<A> annotationType) {
    if (ObjectUtil.isAnyNull(clazz, annotationType)) {
      return null;
    }

    return clazz.getAnnotation(annotationType);
  }

  public static <A extends Annotation> A getAnnotation(Method method, Class<A> annotationType) {
    if (ObjectUtil.isAnyNull(method, annotationType)) {
      return null;
    }

    return method.getAnnotation(annotationType);
  }

  public static <A extends Annotation> A getAnnotation(Field field, Class<A> annotationType) {
    if (ObjectUtil.isAnyNull(field, annotationType)) {
      return null;
    }

    return field.getAnnotation(annotationType);
  }

}
