package com.sm.common.libs.able;

/**
 * 将一个对象转换成另一个对象的接口.
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月28日 下午1:15:01
 * @param <FROM>
 * @param <TO>
 */
public interface Transformer<FROM, TO> {

  /**
   * 对象转换，从<code>FROM</code>类型转变成<code>TO</code>类型
   * 
   * @param from 待转换对象
   * @return 转换后的类型<tt>TO</tt>
   */
  TO transform(FROM from);
}
