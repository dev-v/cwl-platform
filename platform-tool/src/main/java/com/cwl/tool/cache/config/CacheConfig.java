package com.cwl.tool.cache.config;

import com.cwl.tool.cache.ICacheService;
import com.cwl.tool.redis.cache.RedisCacheService;
import com.cwl.tool.redis.config.RedisConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2018/1/11
 */
public class CacheConfig {

    public ICacheService redisCacheService(LettuceConnectionFactory connectionFactory) {
        return new RedisCacheService(connectionFactory);
    }
}
