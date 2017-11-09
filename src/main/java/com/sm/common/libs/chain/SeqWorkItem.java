/**
 * 
 */
package com.sm.common.libs.chain;

import com.sm.common.libs.able.Valuable;

/**
 * 带顺序的工作项目
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年5月9日 下午3:04:49
 */
public interface SeqWorkItem<I, O> extends Valuable<Integer>, WorkItem<I, O> {

}
