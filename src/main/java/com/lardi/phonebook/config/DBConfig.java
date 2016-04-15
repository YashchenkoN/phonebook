package com.lardi.phonebook.config;

import com.lardi.phonebook.dao.FileUserDao;
import com.lardi.phonebook.dao.SQLUserDao;
import com.lardi.phonebook.dao.UserDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author Nikolay Yashchenko
 */
@Configuration
@EnableTransactionManagement
public class DBConfig {

    @Value("${dataSource.driverClassName}")
    private String driver;
    @Value("${dataSource.url}")
    private String url;
    @Value("${dataSource.username}")
    private String username;
    @Value("${dataSource.password}")
    private String password;
    @Value("${db.type:sql}")
    private String dbType;
    @Value("${db.file.name}")
    private String fileName;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public UserDao userDao() {
        if ("sql".equals(dbType)) {
            return new SQLUserDao(dataSource());
        } else {
            return new FileUserDao(fileName);
        }
    }
}
