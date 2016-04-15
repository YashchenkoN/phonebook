package com.lardi.phonebook.config;

import com.lardi.phonebook.PhoneBookApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Nikolay Yashchenko
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = PhoneBookApplication.class)
public class DBConfig {

}
