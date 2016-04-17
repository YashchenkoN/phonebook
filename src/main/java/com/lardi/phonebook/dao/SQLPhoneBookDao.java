package com.lardi.phonebook.dao;

import com.lardi.phonebook.common.MySQL;
import com.lardi.phonebook.entity.PhoneBook;
import com.lardi.phonebook.entity.Role;
import com.lardi.phonebook.entity.User;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikolay Yashchenko
 */
@MySQL
@Repository
public class SQLPhoneBookDao implements PhoneBookDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PhoneBook create(PhoneBook phoneBook) {
        entityManager.persist(phoneBook);
        return phoneBook;
    }

    @Override
    public PhoneBook read(Long id) {
        return entityManager.find(PhoneBook.class, id);
    }

    @Override
    public PhoneBook getByUserId(Long userId) {
        return (PhoneBook) entityManager.unwrap(Session.class).createCriteria(PhoneBook.class)
                .createAlias("owner", "o")
                .add(Restrictions.eq("o.id", userId))
                .uniqueResult();
    }

    @Override
    public PhoneBook update(PhoneBook phoneBook) {
        return entityManager.merge(phoneBook);
    }

    @Override
    public void delete(PhoneBook phoneBook) {
        entityManager.remove(phoneBook);
    }
}
