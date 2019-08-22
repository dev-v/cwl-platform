package com.cwl.tool.mq.config;

import com.cwl.tool.config.RedisConfig;
import com.cwl.tool.mq.IMqManager;
import com.cwl.tool.redis.mq.RedisMqManager;
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
@Configuration
@Import({RedisConfig.class})
public class MQConfig {

  @Bean
  public IMqManager iMqManager(LettuceConnectionFactory connectionFactory) {
    return new RedisMqManager(connectionFactory);
  }
}
