package com.cwl.tool.cache;

import org.apache.commons.collections.map.LRUMap;

import java.util.Map;

/**
 * <pre>
 * 超时特性以后需重构到抽象方法中
 * </pre>
 *
 * @author chenwl 2019-08-29
 */
public class LocalCache extends AbstractCacheService {

  Map<Object, Object> caches;

  public LocalCache(int maxSize) {
    caches = new LRUMap(maxSize);
  }

  public LocalCache() {
    caches = new LRUMap(30_000);
  }

  @Override
  public void put(Object key, Object object) {
    caches.put(key, object);
  }

  @Override
  public void put(Object key, Object object, int deadlineSeconds) {
    caches.put(key, ExpireObject.newInstance(object, deadlineSeconds));
  }

  @Override
  public <T> T get(Object key, Class<T> clz) {
    Object val = caches.get(key);
    if (val == null) {
      return null;
    }

    if (val instanceof ExpireObject) {
      if (((ExpireObject) val).isExpire()) {
        caches.remove(key);
        return null;
      } else {
        return (T) ((ExpireObject) val).val;
      }
    } else {
      return (T) val;
    }
  }

  @Override
  public <T> T get(Object key, Class<T> clz, Callable<T> callable) {
    T t = this.get(key, clz);
    if (t == null) {
      t = callable.call();
      caches.put(key, t);
    }
    return t;
  }

  @Override
  public <T> T get(Object key, Class<T> clz, Callable<T> callable, int expireSeconds) {
    T t = this.get(key, clz);
    if (t == null) {
      t = callable.call();
      if (t != null) {
        caches.put(key, ExpireObject.newInstance(t, expireSeconds));
      }
    }
    return t;
  }

  static final class ExpireObject {
    //过期时间
    private long expireTime;

    Object val;

    private ExpireObject(long expireTime, Object val) {
      this.expireTime = expireTime;
      this.val = val;
    }

    static final ExpireObject newInstance(Object val, int deadlineSeconds) {
      return new ExpireObject(System.currentTimeMillis() + (deadlineSeconds * 1000), val);
    }

    static final ExpireObject newUnExpireObject(Object val) {
      return new ExpireObject(Long.MAX_VALUE, val);
    }

    public boolean isExpire() {
      return System.currentTimeMillis() > expireTime;
    }
  }
}
