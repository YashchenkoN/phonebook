package com.lardi.phonebook.dao;

import com.lardi.phonebook.entity.PhoneBook;

/**
 * @author Nikolay Yashchenko
 */
public interface PhoneBookDao {
    PhoneBook create(PhoneBook phoneBook);
    PhoneBook read(Long id);
    PhoneBook getByUserId(Long userId);
    PhoneBook update(PhoneBook phoneBook);
    void delete(PhoneBook phoneBook);
}
