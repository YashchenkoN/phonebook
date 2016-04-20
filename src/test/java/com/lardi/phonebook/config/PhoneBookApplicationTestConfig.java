package com.lardi.phonebook.config;

import com.lardi.phonebook.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;

@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
		ApplicationConfig.class,
		TestDBConfig.class,
		SecurityConfig.class,
		WebAppInitializer.class
})
public class PhoneBookApplicationTestConfig {

	@Autowired
	private UserService userService;

	@Test
	public void contextLoads() {
		assertNotNull(userService);
	}

}
