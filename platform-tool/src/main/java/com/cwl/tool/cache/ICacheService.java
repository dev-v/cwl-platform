package com.cwl.tool.cache;

import java.util.Deque;

/**
 * <li>文件名称: ICache.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2017年12月23日
 */
public interface ICacheService {

  /**
   * <pre>
   * 根据key缓存对象，适用于一切没有id属性的对象，如：
   *
   * <b>
   * 基础类型及其包装类型、CharSequence及其子类
   * 集合、数组
   * </b>
   * </pre>
   *
   * @param key
   * @param object
   */
  void put(Object key, Object object);

  void put(Object key, Object object, int deadlineSeconds);

  /**
   * <pre>
   * 根据指定的key获取信息
   * 对应 {@link #put(Object, Object)}
   * </pre>
   *
   * @param key
   * @param clz
   * @return
   */
  <T> T get(Object key, Class<T> clz);

  /**
   * <pre>
   * 根据指定的key获取信息
   * 对应 {@link #put(Object, Object)}
   * </pre>
   *
   * @param key
   * @param clz
   * @return
   */
  <T> T get(Object key, Class<T> clz, Callable<T> callable);

  /**
   * <pre>
   * 根据指定的key获取信息
   * 对应 {@link #put(Object, Object)}
   * </pre>
   *
   * @param key
   * @param clz
   * @return
   */
  <T> T get(Object key, Class<T> clz, Callable<T> callable, int expireSeconds);

  /**
   * <pre>
   * 根据id缓存对象，对象必须具有id属性
   * 有id时不管是map还是pojo 推荐使用
   *
   * <b>id必须为整形且已赋值</b>
   * </pre>
   *
   * @param object
   */
  void put(Object object);

  /**
   * <pre>
   * 根据id获取缓存对象
   * 对应 {@link #put(Object)}
   * </pre>
   *
   * @param id
   * @param clz
   * @return
   */
//  <T> T get(Long id, Class<T> clz);

  /**
   * <pre>
   * 根据id获取缓存对象
   * 对应 {@link #put(Object)}
   * </pre>
   *
   * @return
   */
//  void put(Long id, Object object);

  /**
   * <pre>
   * 根据id获取缓存对象
   * 对应 {@link #put(Object)}
   * </pre>
   *
   * @return
   */
//  void put(Long id, Object object, int deadlineSeconds);

  /**
   * <pre>
   * 根据key获取缓存类型deque
   * 之后在该类型上的操作将直接影响缓存中的值
   * </pre>
   *
   * @param key
   * @param clz
   * @return
   */
  <T> Deque<T> getDeque(String key, Class<T> clz, int maxSize);

  @FunctionalInterface
  interface Callable<T> {
    T call();
  }

  int size();
}
