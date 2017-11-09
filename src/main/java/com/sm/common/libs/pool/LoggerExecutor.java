package com.sm.common.libs.pool;

import java.util.List;

import com.sm.common.libs.core.LoggerSupport;

/**
 * just for logger
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月16日 下午5:32:19
 */
public class LoggerExecutor<T> extends LoggerSupport implements BatchExecutor<T> {

  @Override
  public void execute(List<T> records) {
    for (T record : records) {
      logger.info(record.toString());
    }
  }

}
