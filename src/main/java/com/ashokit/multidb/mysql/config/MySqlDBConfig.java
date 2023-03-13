package com.ashokit.multidb.mysql.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.ashokit.multidb.mysql.repository", entityManagerFactoryRef = "mysqlEntityManagerFactory", transactionManagerRef = "mysqlTransactionManager")
public class MySqlDBConfig {

	@Bean(name="mdp")
	@ConfigurationProperties(prefix="mysql.datasource")
	public DataSourceProperties mysqlDataSourceProperties()
	{
		return new DataSourceProperties();
	}
	
	@Bean(name="mysqlDs")
	@Autowired
	public DataSource mysqlDataSource(@Qualifier("mdp") DataSourceProperties dsp)
	{
		return dsp.initializeDataSourceBuilder()
				  .type(HikariDataSource.class)
				  .build();
	}
	
	@Bean
	public EntityManagerFactoryBuilder builder()
	{
		Map<String, Object> jpaProperties=new HashMap<String, Object>();
		jpaProperties.put("hibernate.hbm2ddl.auto", "update");
		jpaProperties.put("hibernate.show-sql", true);
		return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), jpaProperties, null);
	}
	
	@Bean(name="mysqlEntityManagerFactory")
	@Autowired
	public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(@Qualifier("mysqlDs") DataSource ds, EntityManagerFactoryBuilder builder)
	{
		return builder.dataSource(ds).packages("com.ashokit.multidb.mysql.entity").build();
	}
	
	@Primary
	@Bean
	public PlatformTransactionManager mysqlTransactionManager(final @Qualifier("mysqlEntityManagerFactory") EntityManagerFactory mysqlEntityManagerFactory)
	{
		return new JpaTransactionManager(mysqlEntityManagerFactory);
	}
}
