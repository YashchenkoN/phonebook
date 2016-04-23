package com.lardi.phonebook.service;

import com.lardi.phonebook.config.*;
import com.lardi.phonebook.dao.UserDao;
import com.lardi.phonebook.dao.XMLUserDao;
import com.lardi.phonebook.entity.User;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author Nikolay Yashchenko
 */
public class UserDaoTest {

    private UserDao userService = new XMLUserDao();

    @Before
    public void before() {
        new File("users.xml").delete();
        try {
            Files.copy(new File("users_test.xml").toPath(), new File("users.xml").toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        User user = userService.read(3L);
        assertThat(user, notNullValue());
        assertThat(user.getId(), equalTo(3L));
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
