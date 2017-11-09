/**
 * 
 */
package com.sm.common.libs.chain;

import com.sm.common.libs.able.Actionable;

/**
 * 工作项
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年5月9日 下午2:42:58
 */
public interface WorkItem<I, O> extends Actionable<I, O> {

  /** 是否需要进行下一步操作 */
  boolean doNext(O result);

}
