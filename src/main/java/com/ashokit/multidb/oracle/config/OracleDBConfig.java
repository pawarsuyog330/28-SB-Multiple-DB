package com.ashokit.multidb.oracle.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import javax.persistence.EntityManagerFactory;

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

@EnableJpaRepositories(basePackages = "com.ashokit.multidb.oracle.repository", entityManagerFactoryRef = "oracleEntityManagerFactory", transactionManagerRef = "oracleTransactionManager")
public class OracleDBConfig {

	@Bean(name="odp")
	@ConfigurationProperties(prefix="oracle.datasource")
	public DataSourceProperties oracleDataSourceProperties()
	{
		return new DataSourceProperties();
	}
	
	@Bean(name="oracleDs")
	@Autowired
	public DataSource oracleDataSource(@Qualifier("odp") DataSourceProperties dsp)
	{
		return dsp.initializeDataSourceBuilder()
				  .type(HikariDataSource.class)
				  .build();
	}
	
	
	@Bean
	public EntityManagerFactoryBuilder builder2()
	{
		Map<String, Object> jpaProperties=new HashMap<String, Object>();
		jpaProperties.put("hibernate.hbm2ddl.auto", "update");
		jpaProperties.put("hibernate.show-sql", true);
		return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), jpaProperties, null);
	}
	
	@Bean(name="oracleEntityManagerFactory")
	@Autowired
	public LocalContainerEntityManagerFactoryBean oracleEntityManagerFactory(@Qualifier("oracleDs") DataSource ds, EntityManagerFactoryBuilder builder2)
	{
		return builder2.dataSource(ds).packages("com.ashokit.multidb.oracle.entity").build();
	}
	
	@Primary
	@Bean
	public PlatformTransactionManager oracleTransactionManager(final @Qualifier("oracleEntityManagerFactory") EntityManagerFactory oracleEntityManagerFactory)
	{
		return new JpaTransactionManager(oracleEntityManagerFactory);
	}
}
