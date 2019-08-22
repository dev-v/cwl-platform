package com.cwl.tool.config;

import lombok.Data;

/**
 * <li>文件名称: JdbcPoolConfig.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2017年12月20日
 */
@Data
public class JdbcPoolConfig {
  private DbType dbType = DbType.mysql;
  private String host;
  private int port;
  private String parameters;
  private String database;
  private String username;
  private String password;
  private String driver;
  private int initialSize = 5;
  private int maxActive = 20;
  private int minIdle = 5;
  private int maxWait = 1000 * 60 * 10;
  private int maxOpenPreparedStatements = 100;

  /**
   * 查询超时时长 seconds
   */
  private int queryTimeOut = 60;

  private int loginTimeOut = 5;
}
