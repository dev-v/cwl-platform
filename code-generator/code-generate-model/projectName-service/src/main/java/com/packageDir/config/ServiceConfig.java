package com.#{packageName}.config;

import com.cwl.platform.config.PlatformWebServiceConfig;
import com.cwl.platform.mybatis.plugin.pagination.PaginationInterceptor;
import com.cwl.platform.util.DBUtil;
import com.cwl.tool.config.JdbcPoolConfig;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * <li>文件名称: #{projectJavaName}Service.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月21日
 * @author dev-v
 */

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@Import(PlatformWebServiceConfig.class)
@ComponentScan("com.#{packageName}")
@MapperScan(basePackages = {"com.#{packageName}.mapper"})
public class #{projectJavaName}ServiceConfig {

	@Bean
	@ConfigurationProperties(prefix = "#{projectName}.database")
	public JdbcPoolConfig jdbcPoolConfig() {
		return new JdbcPoolConfig();
	}

	@Bean
	public DataSource #{projectName}DataSource() {
		DataSource #{projectName}DataSource = DBUtil.dataSource(jdbcPoolConfig());
		return #{projectName}DataSource;
	}

	@Bean
	public DataSourceTransactionManager #{projectName}TransactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(#{projectName}DataSource());
		return transactionManager;
	}

	@Bean
	public org.apache.ibatis.session.Configuration #{projectName}MybatisConfiguration() {
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.getTypeAliasRegistry().registerAliases("com.#{packageName}.api.entity");
		return configuration;
	}

	@Bean
	public SqlSessionFactoryBean #{projectName}SqlSessionFactoryBean() throws IOException {
		SqlSessionFactoryBean #{projectName}SqlSessionFactoryBean = new SqlSessionFactoryBean();

		#{projectName}SqlSessionFactoryBean.setDataSource(#{projectName}DataSource());

		#{projectName}SqlSessionFactoryBean.setConfiguration(#{projectName}MybatisConfiguration());

		PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
		#{projectName}SqlSessionFactoryBean
				.setMapperLocations(resourceResolver.getResources("classpath*:/mapper/*_mapper.xml"));

		Interceptor[] interceptors = { PaginationInterceptor.getInstance() };
		#{projectName}SqlSessionFactoryBean.setPlugins(interceptors);

		return #{projectName}SqlSessionFactoryBean;
	}
}
