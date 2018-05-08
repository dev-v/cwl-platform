package com.dber.generator.config;

import com.dber.platform.mybatis.plugin.pagination.PaginationInterceptor;
import com.dber.platform.util.DBUtil;
import com.dber.tool.config.JdbcPoolConfig;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * <li>文件名称: ExampleService.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月21日
 * @author dev-v
 */
@Configuration
@EnableConfigurationProperties
@EnableAutoConfiguration
@EnableTransactionManagement
@ComponentScan("com.dber.generator")
@MapperScan(basePackages = { "com.dber.generator.mapper" })
public class DictGeneratorServiceConfig {
	@Bean
	@ConfigurationProperties(prefix = "dber.generator.service.plat")
	public JdbcPoolConfig jdbcPoolConfig() {
		return new JdbcPoolConfig();
	}

	@Bean
	public DataSource dataSource() {
		DataSource dataSource = DBUtil.dataSource(jdbcPoolConfig());
		return dataSource;
	}

	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
		return transactionManager;
	}

	@Bean
	public org.apache.ibatis.session.Configuration mybatisConfiguration() {
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.getTypeAliasRegistry().registerAliases("com.dber.generator.entity");
		return configuration;
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

		sqlSessionFactoryBean.setDataSource(dataSource());

		sqlSessionFactoryBean.setConfiguration(mybatisConfiguration());

		PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(
				resourceResolver.getResources("classpath*:/mapper/*-mapper.xml"));

		Interceptor[] interceptors = { PaginationInterceptor.getInstance() };
		sqlSessionFactoryBean.setPlugins(interceptors);

		return sqlSessionFactoryBean;
	}
}
