package com.lardi.phonebook.service;

import com.lardi.phonebook.entity.User;

/**
 * @author Nikolay Yashchenko
 */
public interface UserService {
    User create(User user);
    User read(Long id);
    User read(String login);
    User update(User user);
    void delete(User user);
}
