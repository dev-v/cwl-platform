package com.cwl.tool.redis.cache;

import java.lang.reflect.Field;

import org.springframework.data.redis.support.collections.DefaultRedisMap;

/**
 * <li>文件名称: ObjectMap.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月23日
 * @author dev-v
 */
public class ObjectMap {
	protected Field idField;

	protected DefaultRedisMap<Long, Object> redisMap;
}
