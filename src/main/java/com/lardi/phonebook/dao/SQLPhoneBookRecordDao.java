package com.lardi.phonebook.dao;

import com.lardi.phonebook.common.Default;
import com.lardi.phonebook.common.MySQL;
import com.lardi.phonebook.entity.PhoneBookRecord;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Nikolay Yashchenko
 */
@Repository
@MySQL
@Default
public class SQLPhoneBookRecordDao implements PhoneBookRecordDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PhoneBookRecord create(PhoneBookRecord phoneBookRecord) {
        entityManager.persist(phoneBookRecord);
        return phoneBookRecord;
    }

    @Override
    public PhoneBookRecord read(Long id) {
        return entityManager.find(PhoneBookRecord.class, id);
    }

    @Override
    public PhoneBookRecord read(String mobilePhone) {
        return (PhoneBookRecord) entityManager.unwrap(Session.class).createCriteria(PhoneBookRecord.class)
                .add(Restrictions.eq("mobilePhone", mobilePhone))
                .uniqueResult();
    }

    @Override
    public PhoneBookRecord update(PhoneBookRecord phoneBookRecord) {
        return entityManager.merge(phoneBookRecord);
    }

    @Override
    public void delete(PhoneBookRecord phoneBookRecord) {
        entityManager.remove(phoneBookRecord);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PhoneBookRecord> getByUserId(Long userId) {
        return entityManager.unwrap(Session.class).createCriteria(PhoneBookRecord.class)
                .createAlias("user", "u")
                .add(Restrictions.eq("u.id", userId))
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PhoneBookRecord> getByFirstName(String firstName, Long userId) {
        return entityManager.unwrap(Session.class).createCriteria(PhoneBookRecord.class)
                .createAlias("user", "u")
                .add(
                        Restrictions.and(
                                Restrictions.eq("u.id", userId),
                                Restrictions.ilike("firstName", "%" + firstName + "%")
                        )
                )
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PhoneBookRecord> getByLastName(String lastName, Long userId) {
        return entityManager.unwrap(Session.class).createCriteria(PhoneBookRecord.class)
                .createAlias("user", "u")
                .add(
                        Restrictions.and(
                                Restrictions.eq("u.id", userId),
                                Restrictions.ilike("lastName", "%" + lastName + "%")
                        )
                )
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PhoneBookRecord> getByPhone(String phone, Long userId) {
        return entityManager.unwrap(Session.class).createCriteria(PhoneBookRecord.class)
                .createAlias("user", "u")
                .add(
                        Restrictions.and(
                                Restrictions.eq("u.id", userId),
                                Restrictions.ilike("mobilePhone", "%" + phone + "%")
                        )
                )
                .list();
    }
}
