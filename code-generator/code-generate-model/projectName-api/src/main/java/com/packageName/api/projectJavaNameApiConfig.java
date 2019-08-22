package com.cwl.#{packageName}.api;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableFeignClients(basePackages = "com.cwl.#{packageName}.api")
@ConditionalOnExpression("#{!environment['spring.application.name'].endsWith('" + #{projectJavaName}ApiConfig.SERVICE_NAME + "')}")
public class #{projectJavaName}ApiConfig {
  public static final String SERVICE_NAME = "#{packageName}-service";
  public static final String PLACE_HOLD_SERVICE_NAME = "${#{projectName}.service." + SERVICE_NAME + ".name:" + SERVICE_NAME + "}";
}
