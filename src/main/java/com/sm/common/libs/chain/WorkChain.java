/**
 * 
 */
package com.sm.common.libs.chain;

import java.util.LinkedList;
import java.util.List;

import com.sm.common.libs.able.Actionable;
import com.sm.common.libs.core.LoggerSupport;

/**
 * 工作链
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年5月9日 下午2:50:25
 */
public class WorkChain<I, O> extends LoggerSupport implements Actionable<I, O> {

  /**
   * 初始化列表
   */
  private final List<WorkItem<I, O>> initList = new LinkedList<>();

  @Override
  public O action(I condition) {
    beforeAction(condition);
    O result = null;
    for (WorkItem<I, O> item : initList) {
      // 初始化处理
      result = item.action(condition);
      // 是否需要下一步骤
      if (!item.doNext(result)) {
        return result;
      }
    }

    afterAction(condition);
    return result;
  }

  protected void beforeAction(I condition) {

  }

  protected void afterAction(I condition) {

  }

  public void register(WorkItem<I, O> item) {
    initList.add(item);
  }

  public void setWorkItems(List<SeqWorkItem<I, O>> items) {
    initList.addAll(items);
  }

}
