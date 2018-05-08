package com.dber.tool.util;

import java.util.HashMap;
import java.util.Map;

/**
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2018/1/15
 */
public class KeyGeneratorUtil {
    private static final Map<Class<?>, String> clz_keys = new HashMap<>();

    public static final String getClzKey(Class<?> clz) {
        String key = clz_keys.get(clz);
        if (key == null) {
            if (Util.isPremitive(clz)) {
                char c = clz.getSimpleName().charAt(0);
                key = "j" + (clz.isPrimitive() ? Character.toUpperCase(c) : c);
                clz_keys.put(clz, key);
            } else {
                String[] names = clz.getName().split("\\.");
                int length = names.length - 1;
                key = names[length];
                for (int i = 0; i < length; i++) {
                    key += names[i].charAt(0);
                }
            }
        }
        return key;
    }

    public static final <T> String generateKey(String key, Class<T> clz) {
        return key + getClzKey(clz);
    }
}
