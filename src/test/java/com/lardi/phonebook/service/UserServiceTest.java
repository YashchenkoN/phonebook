package com.lardi.phonebook.service;

import com.lardi.phonebook.config.*;
import com.lardi.phonebook.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author Nikolay Yashchenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, TestDBConfig.class})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void testCreate() {
        User user = new User();
        user.setName("Name");
        user.setPassword("11111111");
        user.setLogin("login1");

        user = userService.create(user);
        assertNotNull(user);
        assertNotNull(user.getId());

        assertThat(user.getLogin(), equalTo("login1"));
        assertThat(user.getName(), equalTo("Name"));
    }

    @Test
    @Transactional
    public void testRead() {
        User user = userService.read(0L);
        assertThat(user, notNullValue());
        assertThat(user.getId(), equalTo(0L));
        assertThat(user.getName(), equalTo("name"));
        assertThat(user.getLogin(), equalTo("login"));
        assertThat(user.getPassword(), equalTo("1111"));
    }

    @Test
    @Transactional
    public void testUpdate() {
        User user = userService.read(0L);
        assertThat(user, notNullValue());
        assertThat(user.getId(), equalTo(0L));
        assertThat(user.getName(), equalTo("name"));
        assertThat(user.getLogin(), equalTo("login"));
        assertThat(user.getPassword(), equalTo("1111"));

        user.setLogin("login2");
        userService.update(user);
        user = userService.read(0L);

        assertThat(user, notNullValue());
        assertThat(user.getId(), equalTo(0L));
        assertThat(user.getName(), equalTo("name"));
        assertThat(user.getLogin(), equalTo("login2"));
        assertThat(user.getPassword(), equalTo("1111"));
    }

    @Test
    @Transactional
    public void testReadByLogin() {
        User user = userService.read("read_by_login");
        assertThat(user.getLogin(), equalTo("read_by_login"));
    }

    @Test
    @Transactional
    public void testDelete() {
        User user = userService.read(2L);
        assertThat(user.getLogin(), equalTo("delete"));

        userService.delete(user);
        user = userService.read(2L);
        assertThat(user, nullValue());
    }
}
