/**
 * 
 */
package com.sm.common.libs.chain;

import com.sm.common.libs.chain.FilterItem;

/**
 * FilterTwo
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年5月9日 上午2:38:41
 */
public class FilterTwo extends FilterItem {

  @Override
  protected void doFilterInternal(Object object) {
    logger.info("object = [{}],number = two", object);
  }

}
