package com.ssafy.web.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(value = "com.ssafy.web.sec.model.mapper", sqlSessionFactoryRef="tripSecSqlSessionFactory")
//@EnableTransactionManagement
public class TripSecDatabaseConfig {
	
	@Bean(name="tripSecDataSource")
	@ConfigurationProperties(prefix="spring.secdb.datasource") //appliction.properties 참고.
	public DataSource tripSecDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name="tripSecSqlSessionFactory")
	public SqlSessionFactory tripSecSessionFactory(@Qualifier("tripSecDataSource") DataSource db1DataSource, ApplicationContext applicationContext) throws Exception{
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(db1DataSource);
		sessionFactory.setMapperLocations(applicationContext.getResources("classpath:mybatis/mappers/*.xml")); //쿼리작성용 mapper.xml위치 설정.
		return sessionFactory.getObject();
	}
	
	@Bean(name="tripSecSqlSessionTemplate")
	public SqlSessionTemplate tripSecSqlSessionTemplate(SqlSessionFactory tripSecSqlSessionFactory) throws Exception{
		return new SqlSessionTemplate(tripSecSqlSessionFactory);
	}
	
    @Bean(name = "tripSectransactionManager")
	@Primary
    public PlatformTransactionManager transactionManager(@Qualifier("tripSecDataSource") DataSource db1DataSource) {
        return new DataSourceTransactionManager(db1DataSource);
    }
	
}
