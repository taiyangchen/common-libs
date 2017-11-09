/**
 * 
 */
package com.sm.common.libs.chain;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.sm.common.libs.able.Actionable;
import com.sm.common.libs.able.Valuable;
import com.sm.common.libs.core.LoggerSupport;
import com.sm.common.libs.core.ValueComparator;

/**
 * 有顺序的工作链
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年5月9日 下午3:07:45
 */
public class SeqWorkChain<I, O> extends LoggerSupport implements Actionable<I, O> {

  /**
   * 根据枚举<code>Valuable</code>的code值大小进行排序
   */
  private final Comparator<Valuable<Integer>> comparator = ValueComparator.getInstance();

  /**
   * 初始化列表
   */
  private final List<SeqWorkItem<I, O>> initList = new LinkedList<>();

  public void register(SeqWorkItem<I, O> item) {
    initList.add(item);
    Collections.sort(initList, comparator);
  }

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

  public void setWorkItems(List<SeqWorkItem<I, O>> items) {
    initList.addAll(items);
    Collections.sort(initList, comparator);
  }

}
