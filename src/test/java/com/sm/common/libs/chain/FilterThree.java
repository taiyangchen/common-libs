/**
 * 
 */
package com.sm.common.libs.chain;

import com.sm.common.libs.chain.FilterItem;

/**
 * FilterThree
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年5月9日 上午2:38:56
 */
public class FilterThree extends FilterItem {

  @Override
  protected void doFilterInternal(Object object) {
    logger.info("object = [{}],number = three", object);
  }

}
