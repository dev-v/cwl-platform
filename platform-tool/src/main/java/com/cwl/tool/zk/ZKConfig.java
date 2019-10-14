package com.cwl.tool.zk;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <pre>
 *
 * </pre>
 *
 * @author chenwl 2019/10/14
 */
@Data
@ConfigurationProperties("zk")
public class ZKConfig {
  private String host;

  private String rootPath;

  private int retryConnectionIntervalSeconds = 30;
}
