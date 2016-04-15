package com.lardi.phonebook.config;

import com.lardi.phonebook.dao.FileUserDao;
import com.lardi.phonebook.dao.SQLUserDao;
import com.lardi.phonebook.dao.UserDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * @author Nikolay Yashchenko
 */
@Configuration
public class TestDBConfig {
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("/import.sql")
                .build();
    }

    @Bean(name = "sqlUserDao")
    public UserDao userDao() {
        return new SQLUserDao(dataSource());
    }

    @Bean(name = "fileUserDao")
    public UserDao fileUserDao() {
        return new FileUserDao("test.csv");
    }
}
