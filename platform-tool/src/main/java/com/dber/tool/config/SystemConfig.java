package com.dber.tool.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties("dber")
@Data
public class SystemConfig {
  private JdbcPoolConfig database = new JdbcPoolConfig();
}
