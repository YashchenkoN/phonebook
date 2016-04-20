package com.lardi.phonebook.config;

import com.lardi.phonebook.PhoneBookApplication;
import com.lardi.phonebook.common.MySQL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

/**
 * @author Nikolay Yashchenko
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = PhoneBookApplication.class)
public class TestDBConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .build();
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("com.lardi");
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put(USE_SECOND_LEVEL_CACHE, false);
        jpaProperties.put(USE_QUERY_CACHE, false);
        jpaProperties.put(HBM2DDL_AUTO, "create");
        jpaProperties.put(HBM2DDL_IMPORT_FILES, "import.sql");
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }


    @Bean(name = "transactionManager")
    @Primary
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
