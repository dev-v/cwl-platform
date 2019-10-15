package com.cwl.tool.cache;

import java.util.Deque;

/**
 * <pre>
 *
 * </pre>
 *
 * @author chenwl 2019-08-29
 */
public abstract class AbstractCacheService implements ICacheService {

  @Override
  public void put(Object key, Object object, int deadlineSeconds) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> T get(Object key, Class<T> clz, Callable<T> callable) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> T get(Object key, Class<T> clz, Callable<T> callable, int expireSeconds) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void put(Object object) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> Deque<T> getDeque(String key, Class<T> clz, int maxSize) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void remove(Object key) {
    throw new UnsupportedOperationException();
  }
}
