package com.dber.tool.redis.mq;

import com.dber.tool.mq.Publisher;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * <li>文件名称: RedisPubliser.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月23日
 * @author dev-v
 */
public class RedisPubliser implements Publisher {

	private RedisTemplate<String, String> template;

	private String systemKey;

	protected RedisPubliser(String systemKey, RedisTemplate<String, String> template) {
		this.template = template;
		this.systemKey = systemKey;
	}

	@Override
	public void pub(String key, Object object) {
		template.convertAndSend(systemKey + key.trim(), object);
	}

}
