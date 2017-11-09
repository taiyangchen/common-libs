/**
 * 
 */
package com.sm.common.libs.chain;

/**
 * 通用过滤器
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年5月9日 上午2:12:54
 */
public interface Filter {

  void doFilter(Object object, FilterChain chain);

}
