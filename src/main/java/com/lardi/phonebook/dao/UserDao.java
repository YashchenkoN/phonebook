package com.lardi.phonebook.dao;

import com.lardi.phonebook.entity.User;

/**
 * @author Nikolay Yashchenko
 */
public interface UserDao {
    User create(User user);
    User read(Long id);
    User read(String login);
    Long getId(String login);
    User update(User user);
    void delete(User user);
}
