/**
 * 
 */
package com.sm.common.libs.pool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.sm.common.libs.able.Keyable;

/**
 * 批量的缓冲池
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月17日 下午1:45:04
 * @param <T>
 */
public class BatchBuffer<T extends Keyable<String>> implements Buffer<T> {

  /**
   * 缓冲池集
   */
  private List<Buffer<T>> buffers = new ArrayList<>();

  /**
   * 缓冲池映射表
   */
  private final Map<String, Buffer<T>> bufferMap = new HashMap<>();

  /**
   * 缓冲池名称
   */
  private String name;

  /**
   * 是否激活
   */
  private boolean active;

  public BatchBuffer() {
    this.name = this.getClass().getSimpleName();
  }

  public BatchBuffer(String name) {
    this.name = name;
  }

  @Override
  public void start() throws Exception {
    for (Buffer<T> buffer : buffers) {
      buffer.start();
    }

    active = true;
  }

  @Override
  public void stop() throws Exception {
    for (Buffer<T> buffer : buffers) {
      buffer.stop();
    }

    active = false;
  }

  /**
   * 添加缓冲池 @see Buffer
   * 
   * @param buffer 缓冲池
   */
  public void add(Buffer<T> buffer) {
    buffers.add(buffer);
    bufferMap.put(buffer.getName(), buffer);
  }

  @Override
  public boolean add(T record) {
    Buffer<T> buffer = bufferMap.get(record.getId());
    return (buffer == null) ? false : buffer.add(record);
  }

  @Override
  public boolean add(T record, long timeout, TimeUnit unit) {
    Buffer<T> buffer = bufferMap.get(record.getId());
    return (buffer == null) ? false : buffer.add(record, timeout, unit);
  }

  /**
   * 清理
   */
  public void clean() {
    buffers.clear();
    bufferMap.clear();
  }

  /**
   * 移除缓冲池
   * 
   * @param key 缓冲池的Key
   */
  public void remove(String key) {
    Buffer<T> buffer = bufferMap.remove(key);
    if (buffer != null) {
      buffers.remove(buffer);
    }
  }

  /**
   * 移除缓冲池
   * 
   * @param buffer 缓冲池 @see Buffer
   */
  public void remove(Buffer<T> buffer) {
    buffers.remove(buffer);
    bufferMap.remove(buffer.getName());
  }

  @Override
  public String getName() {
    return name;
  }

  public void setBuffers(List<Buffer<T>> buffers) {
    this.buffers = buffers;
    for (Buffer<T> buffer : buffers) {
      bufferMap.put(buffer.getName(), buffer);
    }
  }

  @Override
  public boolean isActive() {
    return active;
  }

}
