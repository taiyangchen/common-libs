/**
 * 
 */
package com.sm.common.libs.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 有关 <code>Class</code> 处理的工具类。
 * 
 * <p>
 * 这个类中的每个方法都可以“安全”地处理 <code>null</code> ，而不会抛出 <code>NullPointerException</code>。
 * </p>
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月22日 下午1:54:08
 */
public abstract class ClassUtil {

  // ==========================================================================
  // 取得友好类名和package名的方法。
  // ==========================================================================

  /**
   * 取得对象所属的类的友好类名。
   * <p>
   * 类似<code>object.getClass().getName()</code>，但不同的是，该方法用更友好的方式显示数组类型。 例如：
   * </p>
   * <p/>
   * 
   * <pre>
   *  int[].class.getName() = "[I"
   *  ClassUtil.getFriendlyClassName(int[].class) = "int[]"
   * 
   *  Integer[][].class.getName() = "[[Ljava.lang.Integer;"
   *  ClassUtil.getFriendlyClassName(Integer[][].class) = "java.lang.Integer[][]"
   * </pre>
   * <p>
   * 对于非数组的类型，该方法等效于 <code>Class.getName()</code> 方法。
   * </p>
   * <p>
   * 注意，该方法所返回的数组类名只能用于显示给人看，不能用于 <code>Class.forName</code> 操作。
   * </p>
   * 
   * @param object 要显示类名的对象
   * @return 用于显示的友好类名，如果对象为空，则返回<code>null</code>
   */
  public static String getFriendlyClassName(Object object) {
    if (object == null) {
      return null;
    }

    String javaClassName = object.getClass().getName();

    return toFriendlyClassName(javaClassName, true, javaClassName);
  }

  /**
   * 取得友好的类名。
   * <p>
   * 类似<code>clazz.getName()</code>，但不同的是，该方法用更友好的方式显示数组类型。 例如：
   * </p>
   * <p/>
   * 
   * <pre>
   *  int[].class.getName() = "[I"
   *  ClassUtil.getFriendlyClassName(int[].class) = "int[]"
   * 
   *  Integer[][].class.getName() = "[[Ljava.lang.Integer;"
   *  ClassUtil.getFriendlyClassName(Integer[][].class) = "java.lang.Integer[][]"
   * </pre>
   * <p>
   * 对于非数组的类型，该方法等效于 <code>Class.getName()</code> 方法。
   * </p>
   * <p>
   * 注意，该方法所返回的数组类名只能用于显示给人看，不能用于 <code>Class.forName</code> 操作。
   * </p>
   * 
   * @param object 要显示类名的对象
   * @return 用于显示的友好类名，如果类对象为空，则返回<code>null</code>
   */
  public static String getFriendlyClassName(Class<?> clazz) {
    if (clazz == null) {
      return null;
    }

    String javaClassName = clazz.getName();

    return toFriendlyClassName(javaClassName, true, javaClassName);
  }

  /**
   * 取得友好的类名。
   * <p>
   * <code>className</code> 必须是从 <code>clazz.getName()</code> 所返回的合法类名。该方法用更友好的方式显示数组类型。 例如：
   * </p>
   * <p/>
   * 
   * <pre>
   *  int[].class.getName() = "[I"
   *  ClassUtil.getFriendlyClassName(int[].class) = "int[]"
   * 
   *  Integer[][].class.getName() = "[[Ljava.lang.Integer;"
   *  ClassUtil.getFriendlyClassName(Integer[][].class) = "java.lang.Integer[][]"
   * </pre>
   * <p>
   * 对于非数组的类型，该方法等效于 <code>Class.getName()</code> 方法。
   * </p>
   * <p>
   * 注意，该方法所返回的数组类名只能用于显示给人看，不能用于 <code>Class.forName</code> 操作。
   * </p>
   * 
   * @param javaClassName 要转换的类名
   * @return 用于显示的友好类名，如果原类名为空，则返回 <code>null</code> ，如果原类名是非法的，则返回原类名
   */
  public static String getFriendlyClassName(String javaClassName) {
    return toFriendlyClassName(javaClassName, true, javaClassName);
  }

  /**
   * 将Java类名转换成友好类名。
   * 
   * @param javaClassName Java类名
   * @param processInnerClass 是否将内联类分隔符 <code>'$'</code> 转换成 <code>'.'</code>
   * @return 友好的类名。如果参数非法或空，则返回<code>null</code>。
   */
  static String toFriendlyClassName(String javaClassName, boolean processInnerClass, String defaultIfInvalid) {
    String name = StringUtils.trimToNull(javaClassName);

    if (name == null) {
      return defaultIfInvalid;
    }

    if (processInnerClass) {
      name = name.replace('$', '.');
    }

    int length = name.length();
    int dimension = 0;

    // 取得数组的维数，如果不是数组，维数为0
    for (int i = 0; i < length; i++, dimension++) {
      if (name.charAt(i) != '[') {
        break;
      }
    }

    // 如果不是数组，则直接返回
    if (dimension == 0) {
      return name;
    }

    // 确保类名合法
    if (length <= dimension) {
      return defaultIfInvalid; // 非法类名
    }

    // 处理数组
    StringBuilder componentTypeName = new StringBuilder();

    switch (name.charAt(dimension)) {
      case 'Z':
        componentTypeName.append("boolean");
        break;

      case 'B':
        componentTypeName.append("byte");
        break;

      case 'C':
        componentTypeName.append("char");
        break;

      case 'D':
        componentTypeName.append("double");
        break;

      case 'F':
        componentTypeName.append("float");
        break;

      case 'I':
        componentTypeName.append("int");
        break;

      case 'J':
        componentTypeName.append("long");
        break;

      case 'S':
        componentTypeName.append("short");
        break;

      case 'L':
        if (name.charAt(length - 1) != ';' || length <= dimension + 2) {
          return defaultIfInvalid; // 非法类名
        }

        componentTypeName.append(name.substring(dimension + 1, length - 1));
        break;

      default:
        return defaultIfInvalid; // 非法类名
    }

    for (int i = 0; i < dimension; i++) {
      componentTypeName.append("[]");
    }

    return componentTypeName.toString();
  }

  /**
   * 判断是否有超类
   * 
   * @param clazz 目标类
   * @return 如果有返回<code>true</code>，否则返回<code>false</code>
   */
  public static boolean hasSuperClass(Class<?> clazz) {
    return (clazz != null) && !clazz.equals(Object.class);
  }

  /**
   * 获取对象数组的类型数组
   * 
   * @param objects 对象数组
   * @return 类型数组
   */
  public static Class<?>[] objects2Classes(final Object... objects) {
    if (ArrayUtils.isEmpty(objects)) {
      return ArrayUtils.EMPTY_CLASS_ARRAY;
    }

    int objectLength = objects.length;
    Class<?>[] classes = new Class[objectLength];
    for (int i = 0; i < objectLength; i++) {
      // FIXME primitive and wrapper
      Class<?> clazz = ClassUtils.wrapperToPrimitive(objects[i].getClass());
      classes[i] = (clazz != null) ? clazz : objects[i].getClass();
    }

    return classes;
  }

  public static boolean isJdk(Class<?> clazz) {
    if (clazz == null) {
      return false;
    }

    return clazz.isArray() || clazz.isPrimitive() || clazz.getPackage().getName().startsWith("java.");
  }

}
