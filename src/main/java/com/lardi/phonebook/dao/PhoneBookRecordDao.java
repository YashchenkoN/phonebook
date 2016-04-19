package com.lardi.phonebook.dao;

import com.lardi.phonebook.entity.PhoneBookRecord;

import java.util.List;

/**
 * @author Nikolay Yashchenko
 */
public interface PhoneBookRecordDao {
    PhoneBookRecord create(PhoneBookRecord phoneBookRecord);
    PhoneBookRecord read(Long id);
    PhoneBookRecord read(String mobilePhone);
    PhoneBookRecord update(PhoneBookRecord phoneBookRecord);
    void delete(PhoneBookRecord phoneBookRecord);
    List<PhoneBookRecord> getByUserId(Long userId);
    List<PhoneBookRecord> getByFirstName(String firstName, Long userId);
    List<PhoneBookRecord> getByLastName(String lastName, Long userId);
    List<PhoneBookRecord> getByPhone(String phone, Long userId);
}
