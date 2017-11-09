/**
 * 
 */
package com.sm.common.libs.chain;

/**
 * 过滤器链
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年5月9日 上午2:15:14
 */
public interface FilterChain {
  
  void addFilter(Filter filter);

  void doFilter(Object object);

}
