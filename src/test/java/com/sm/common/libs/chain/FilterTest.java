/**
 * 
 */
package com.sm.common.libs.chain;

import org.junit.Test;

import com.sm.common.libs.chain.DefaultFilter;
import com.sm.common.libs.chain.DefaultFilterChain;
import com.sm.common.libs.chain.Filter;
import com.sm.common.libs.chain.FilterChain;

/**
 * FilterTest
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年5月9日 上午2:42:50
 */
public class FilterTest {

  @Test
  public void test() {
    FilterChain chain = new DefaultFilterChain();
    chain.addFilter(new FilterOne());
    chain.addFilter(new FilterTwo());
    chain.addFilter(new FilterThree());
    Filter filter = new DefaultFilter();
    filter.doFilter("test_xxx", chain);
  }

}
