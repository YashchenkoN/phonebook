package com.lardi.phonebook.service;

import com.lardi.phonebook.dao.UserDao;
import com.lardi.phonebook.entity.Role;
import com.lardi.phonebook.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nikolay Yashchenko
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        return userDao.create(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User read(Long id) {
        return userDao.read(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User read(String login) {
        return userDao.read(login);
    }

    @Transactional
    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Transactional
    @Override
    public void delete(User user) {
        userDao.delete(user);
    }
}
