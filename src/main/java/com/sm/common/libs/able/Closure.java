package com.sm.common.libs.able;

/**
 * 仿闭包，接口中的 {@link #execute(Object...)} 通过回调模拟闭包。
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月22日 上午11:56:22
 */
public interface Closure {
  /**
   * Performs an action on the specified input object.
   * 
   * @param input the input to execute on
   */
  public void execute(Object... input);
}
