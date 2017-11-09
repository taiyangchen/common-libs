/**
 * 
 */
package com.sm.common.libs.chain;

import com.sm.common.libs.core.LoggerSupport;

/**
 * 默认的过滤器
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年5月9日 上午3:05:06
 */
public class DefaultFilter extends LoggerSupport implements Filter {

  @Override
  public void doFilter(Object object, FilterChain chain) {
    chain.doFilter(object);
  }

}
