/**
 * 
 */
package com.sm.common.libs.chain;

import com.sm.common.libs.core.LoggerSupport;

/**
 * 过滤器模版
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年5月9日 上午2:51:20
 */
public abstract class FilterItem extends LoggerSupport implements Filter {

  @Override
  public void doFilter(Object object, FilterChain chain) {
    doFilterInternal(object);
    chain.doFilter(object);
  }
  
  protected abstract void doFilterInternal(Object object);

}
