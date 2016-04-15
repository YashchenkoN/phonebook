package com.lardi.phonebook.dao;

import com.lardi.phonebook.config.PhoneBookApplicationTestConfig;
import com.lardi.phonebook.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author Nikolay Yashchenko
 */
public class FileUserDaoTest extends PhoneBookApplicationTestConfig {

    @Autowired
    @Qualifier("fileUserDao")
    private UserDao userDao;

    @Test
    public void testCreate() {
        User user = new User();
        user.setName("Name");
        user.setPassword("11111111");
        user.setLogin("login1");

        user = userDao.create(user);
        assertNotNull(user);
        assertNotNull(user.getId());

        assertThat(user.getLogin(), equalTo("login1"));
        assertThat(user.getName(), equalTo("Name"));
        assertThat(user.getPassword(), equalTo("11111111"));
    }

}
