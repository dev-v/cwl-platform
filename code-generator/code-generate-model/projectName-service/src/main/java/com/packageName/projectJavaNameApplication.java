package com.cwl.#{packageName};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <li>文件名称: projectJavaNameApplication.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @version 1.0
 * @since 2017年12月21日
 * @author dev-v
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration
public class #{projectJavaName}Application {
	public static void main(String[] args) {
		SpringApplication.run(#{projectJavaName}Application.class, args);
	}
}
