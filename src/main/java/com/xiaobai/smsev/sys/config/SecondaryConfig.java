package com.xiaobai.smsev.sys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "secondaryEntityManagerFactory",
    transactionManagerRef = "secondaryTransactionManager",
    basePackages = {"com.xiaobai.smsev.model"}) //设置Repository所在位置
public class SecondaryConfig {
    @Autowired
    private Environment env;
    @Resource
    @Qualifier("secondaryDataSource")
    private DataSource secondaryDataSource;
    @Primary
    @Bean(name = "secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory =new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(secondaryDataSource);
        factory.setPackagesToScan("com.xiaobai.smsev.model");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        jpaProperties.put("hibernate.show-sql", env.getProperty("hibernate.show-sql"));
        factory.setJpaProperties(jpaProperties);
        return factory;
    }

    @Primary
    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager primaryTransactionManager() {
        EntityManagerFactory factory = primaryEntityManagerFactory().getObject();
        return new JpaTransactionManager(factory);
    }
}
