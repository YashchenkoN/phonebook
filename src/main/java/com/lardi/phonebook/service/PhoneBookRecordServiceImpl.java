package com.lardi.phonebook.service;

import com.lardi.phonebook.dao.PhoneBookRecordDao;
import com.lardi.phonebook.entity.PhoneBookRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Nikolay Yashchenko
 */
@Service
public class PhoneBookRecordServiceImpl implements PhoneBookRecordService {

    @Autowired
    private PhoneBookRecordDao phoneBookRecordDao;

    @Transactional
    @Override
    public PhoneBookRecord create(PhoneBookRecord phoneBookRecord) {
        return phoneBookRecordDao.create(phoneBookRecord);
    }

    @Transactional(readOnly = true)
    @Override
    public PhoneBookRecord read(Long id) {
        return phoneBookRecordDao.read(id);
    }

    @Transactional(readOnly = true)
    @Override
    public PhoneBookRecord read(String mobilePhone) {
        return phoneBookRecordDao.read(mobilePhone);
    }

    @Transactional
    @Override
    public PhoneBookRecord update(PhoneBookRecord phoneBookRecord) {
        return phoneBookRecordDao.update(phoneBookRecord);
    }

    @Transactional
    @Override
    public void delete(PhoneBookRecord phoneBookRecord) {
        phoneBookRecordDao.delete(phoneBookRecord);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        PhoneBookRecord phoneBookRecord = read(id);
        if (phoneBookRecord != null) {
            delete(phoneBookRecord);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<PhoneBookRecord> getByUserId(Long userId) {
        return phoneBookRecordDao.getByUserId(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PhoneBookRecord> getByFirstName(String firstName, Long userId) {
        return phoneBookRecordDao.getByFirstName(firstName, userId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PhoneBookRecord> getByLastName(String lastName, Long userId) {
        return phoneBookRecordDao.getByLastName(lastName, userId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PhoneBookRecord> getByPhone(String phone, Long userId) {
        return phoneBookRecordDao.getByPhone(phone, userId);
    }
}
