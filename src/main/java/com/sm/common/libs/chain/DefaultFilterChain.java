/**
 * 
 */
package com.sm.common.libs.chain;

import java.util.ArrayList;
import java.util.List;

import com.sm.common.libs.core.LoggerSupport;
import com.sm.common.libs.util.CollectionUtil;

/**
 * 默认的过滤器链
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年5月9日 上午2:32:22
 */
public class DefaultFilterChain extends LoggerSupport implements FilterChain {

  private List<Filter> filters;
  private int index;
  
  public DefaultFilterChain() {
    filters = new ArrayList<>();
  }
  
  @Override
  public void addFilter(Filter filter) {
    filters.add(filter);
  }

  @Override
  public void doFilter(Object object) {
    if (CollectionUtil.isEmpty(filters)) {
      return;
    }
    if (index == filters.size()) {
      return;
    }

    Filter nextFilter = filters.get(index++);
    nextFilter.doFilter(object, this);
  }

}
