package com.dber.tool.redis.config;

import com.dber.tool.config.SpringConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.DefaultLettucePool;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import org.springframework.data.redis.connection.lettuce.LettucePool;

/**
 * <li>文件名称: RedisConfig.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2017年12月22日
 */
@EnableConfigurationProperties({SpringConfig.class})
@Configuration
//@EnableCaching 容器中需要有CacheManager才可以使用
public class RedisConfig {

  @Autowired
  SpringConfig springConfig;

  @Bean
  public LettucePool lettucePool() {
    com.dber.tool.config.RedisConfig config = springConfig.getRedis();
    DefaultLettucePool pool = new DefaultLettucePool(config.getHost(), config.getPort(), config.getPool());
    return pool;
  }

  @Bean
  public LettuceConnectionFactory lettuceConnectionFactory(LettucePool pool) {
    LettuceConnectionFactory factory = new LettuceConnectionFactory(pool);
    return factory;
  }

  /**
   * <pre>
   * 启用注解管理cache
   *
   * &#64;Cacheable triggers cache population
   *
   * &#64;CacheEvict triggers cache eviction
   *
   * &#64;CachePut updates the cache without interfering with the method execution
   *
   * &#64;Caching regroups multiple cache operations to be applied on a method
   *
   * &#64;CacheConfig shares some common cache-related settings at class-level
   * </pre>
   *
   * @param connectionFactory
   * @return
   */
  public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
    return null;
  }

  /**
   * <pre>
   * 注解缓存默认key生成策略
   * org.springframework.cache.interceptor.DefaultKeyGenerator
   *
   * If no params are given, return SimpleKey.EMPTY.
   *
   * If only one param is given, return that instance.
   *
   * If more the one param is given, return a SimpleKey containing all parameters.
   * </pre>
   *
   * @return
   */
  public KeyGenerator keyGenerator() {
    return null;
  }
}
