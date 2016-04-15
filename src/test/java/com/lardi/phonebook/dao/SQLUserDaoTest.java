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
public class SQLUserDaoTest extends PhoneBookApplicationTestConfig {

    @Autowired
    @Qualifier("sqlUserDao")
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

    @Test
    public void testRead() {
        User user = userDao.read(0L);
        assertThat(user, notNullValue());
        assertThat(user.getId(), equalTo(0L));
        assertThat(user.getName(), equalTo("name"));
        assertThat(user.getLogin(), equalTo("login"));
        assertThat(user.getPassword(), equalTo("1111"));
    }

    @Test
    public void testUpdate() {
        User user = userDao.read(0L);
        assertThat(user, notNullValue());
        assertThat(user.getId(), equalTo(0L));
        assertThat(user.getName(), equalTo("name"));
        assertThat(user.getLogin(), equalTo("login"));
        assertThat(user.getPassword(), equalTo("1111"));

        user.setLogin("login2");
        userDao.update(user);
        user = userDao.read(0L);

        assertThat(user, notNullValue());
        assertThat(user.getId(), equalTo(0L));
        assertThat(user.getName(), equalTo("name"));
        assertThat(user.getLogin(), equalTo("login2"));
        assertThat(user.getPassword(), equalTo("1111"));
    }

    @Test
    public void testReadByLogin() {
        User user = userDao.read("read_by_login");
        assertThat(user.getLogin(), equalTo("read_by_login"));
    }

    @Test
    public void testDelete() {
        User user = userDao.read(2L);
        assertThat(user.getLogin(), equalTo("delete"));

        userDao.delete(user);
        user = userDao.read(2L);
        assertThat(user, nullValue());
    }
}
