/**
 * 
 */
package com.sm.common.libs.chain;

/**
 * 简单的基类
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年5月9日 下午2:45:00
 */
public abstract class BaseSeqWorkItem<I, O> extends BaseWorkItem<I, O> implements SeqWorkItem<I, O> {

  protected Enum<?> _enum;

  protected BaseSeqWorkItem() {

  }

  @Override
  public Integer value() {
    if (_enum == null) {
      return -1;
    }

    return _enum.ordinal();
  }

}
