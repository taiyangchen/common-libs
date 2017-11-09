package com.sm.common.libs.bus;

import java.util.List;

/**
 * 代表一个总线成员
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月11日 上午1:22:38
 */
public interface BusMember {
  /**
   * 绑定消息
   * 
   * @param busName 消息类型
   */
  void bind(String busName);

  /**
   * 取消绑定
   * 
   * @param busName 消息类型
   */
  void unbind(String busName);

  /**
   * 获取所有注册消息
   * 
   * @return 所有注册消息
   */
  List<String> getRegisteredBuses();

  /**
   * 处理器
   * 
   * @return 处理器
   */
  Object getMemberHandle();

  /**
   * 设置处理器
   * 
   * @param handle 处理器
   */
  void setMemberHandle(Object handle);
}
