/**
 * 
 */
package com.sm.common.libs.util;

import org.apache.commons.lang3.StringUtils;

/**
 * EnumUtil
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年9月21日 下午7:19:09
 */
public abstract class EnumUtil {

  public static <E extends Enum<E>> E parseName(Class<E> enumType, String name) {
    if (enumType == null || StringUtils.isBlank(name)) {
      return null;
    }

    return Enum.valueOf(enumType, name);
  }

  public static <E extends Enum<E>> E parseOrdinal(Class<E> enumType, int ordinal) {
    if (enumType == null || ordinal < 0) {
      return null;
    }
    
    if(ordinal > enumType.getEnumConstants().length) {
      return null;
    }

    return enumType.getEnumConstants()[ordinal];
  }

  public static <E extends Enum<E>> E matchByOrdinal(Class<E> destType, Enum<?> srcEnum) {
    if (ObjectUtil.isAnyNull(destType, srcEnum)) {
      return null;
    }
    
    int ordinal = srcEnum.ordinal();
    if(ordinal > destType.getEnumConstants().length) {
      return null;
    }
    
    return destType.getEnumConstants()[ordinal];
  }

}
