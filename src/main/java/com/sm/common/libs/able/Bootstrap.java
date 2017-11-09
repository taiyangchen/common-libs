/**
 * 
 */
package com.sm.common.libs.able;

/**
 * 代表一个引导程序，可激活关闭
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月9日 下午1:54:25
 */
public interface Bootstrap extends Startable, Stopable {

  /**
   * 是否激活
   * 
   * @return 如果是则返回<code>true</code>
   */
  boolean isActive();

}
