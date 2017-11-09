package com.sm.common.libs.bus;

import java.util.ArrayList;
import java.util.List;

import com.sm.common.libs.core.LoggerSupport;
import com.sm.common.libs.core.SimpleThreadPool;
import com.sm.common.libs.util.CastUtil;

/**
 * 总线分发
 * 
 * @author <a href="mailto:xuchen06@baidu.com">xuc</a>
 * @version create on 2015-1-8 下午10:29:38
 */
public class BusDispatcher extends LoggerSupport {

  /**
   * 总线成员 @see BusMember
   */
  private final List<BusMember> members;

  public BusDispatcher() {
    members = new ArrayList<>();
  }

  /**
   * 绑定
   * 
   * @param member 总线成员 @see BusMember
   */
  public void bind(BusMember member) {
    members.add(member);
  }

  /**
   * 解绑
   * 
   * @param member 总线成员 @see BusMember
   */
  public void unbind(BusMember member) {
    members.remove(member);
  }

  /**
   * 发送信息
   * 
   * @param data 信息 @see ActionData
   */
  public <T> void push(ActionData<T> data) {
    for (int i = 0, membersSize = members.size(); i < membersSize; i++) {
      Object member = members.get(i);
      if (member instanceof BusSubscriber) {
        try {
          BusSubscriber<T> subscriber = CastUtil.cast(member);
          subscriber.receive(data);
        } catch (Exception e) {
          logger.error("caught exception dispatching data to member index " + i + "\n data := " + data, e);
        }
      }
    }
  }

  /**
   * 获取信息
   * 
   * @param data 信息 @see ActionData
   * @param asyn 是否异步
   */
  public <T> void push(ActionData<T> data, boolean asyn) {
    final ActionData<T> fData = data;
    if (asyn) {
      SimpleThreadPool.getInstance().execute("BusDispatcher.push", new Runnable() {
        @Override
        public void run() {
          push(fData);
        }
      });
      return;
    }

    push(data);
  }

}
