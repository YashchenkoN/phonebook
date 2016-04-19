package com.lardi.phonebook.config;

import com.lardi.phonebook.PhoneBookApplication;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Nikolay Yashchenko
 */
@Configuration
@ComponentScan(basePackageClasses = PhoneBookApplication.class)
@EnableAspectJAutoProxy
public class ApplicationConfig {

    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setIgnoreUnresolvablePlaceholders(true);
        ppc.setLocation(new ClassPathResource("application.properties"));
        return ppc;
    }

}
