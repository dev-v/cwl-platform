package com.cwl.tool.util;

import org.apache.commons.collections.map.LRUMap;

/**
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2018/1/24
 */
public class ExpireMap<K, V> {
    private LRUMap map;

    private int expireSeconds;

    public ExpireMap(int maxSize, int expireSeconds) {
        this.map = new LRUMap(maxSize);
        this.expireSeconds = expireSeconds;
    }

    public V get(K key) {
        Value value = (Value) map.get(key);
        if (value == null) {
            return null;
        }
        int time = (int) ((System.currentTimeMillis() - value.getCreateTime()) / 1000);
        if (time > expireSeconds) {
            map.remove(key);
            return null;
        }
        return (V) value.getValue();
    }

    public void put(K key, V value) {
        map.put(key, new Value(value));
    }

    private static final class Value {
        private long createTime;
        private Object value;

        Value(Object value) {
            this.createTime = System.currentTimeMillis();
            this.value = value;
        }

        public long getCreateTime() {
            return createTime;
        }

        public Object getValue() {
            return value;
        }
    }
}
