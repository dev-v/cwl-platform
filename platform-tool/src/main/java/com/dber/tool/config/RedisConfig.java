package com.dber.tool.config;

import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2018/1/12
 */
@Data
public class RedisConfig {
    private String host;

    private int port;

    private GenericObjectPoolConfig pool = new GenericObjectPoolConfig();
}
