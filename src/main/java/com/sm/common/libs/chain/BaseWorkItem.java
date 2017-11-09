/**
 * 
 */
package com.sm.common.libs.chain;

import com.sm.common.libs.core.LoggerSupport;

/**
 * 简单的基类
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年5月9日 下午2:45:00
 */
public abstract class BaseWorkItem<I, O> extends LoggerSupport implements WorkItem<I, O> {

  protected BaseWorkItem() {

  }

  @Override
  public O action(I condition) {
    // 处理初始化流程
    O result = doAction(condition);
    // 若处理失败，输出失败信息
    if (!isSuccess(result)) {
      fail(condition);
    }

    return result;
  }

  protected abstract O doAction(I condition);

  /**
   * 失败时处理
   */
  protected abstract void fail(I condition);

  protected abstract boolean isSuccess(O result);

}
