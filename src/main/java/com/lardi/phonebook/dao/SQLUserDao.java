package com.lardi.phonebook.dao;

import com.lardi.phonebook.common.DefaultProfile;
import com.lardi.phonebook.common.MySQLProfile;
import com.lardi.phonebook.entity.User;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Nikolay Yashchenko
 */
@Repository
@MySQLProfile
@DefaultProfile
public class SQLUserDao implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User create(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public User read(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User read(String login) {
        return (User) entityManager.unwrap(Session.class).createCriteria(User.class)
                .add(Restrictions.eq("login", login))
                .uniqueResult();
    }

    @Override
    public Long getId(String login) {
        return (Long) entityManager.unwrap(Session.class).createCriteria(User.class)
                .add(Restrictions.eq("login", login))
                .setProjection(Projections.property("id"))
                .uniqueResult();
    }

    @Override
    public User update(User user) {
        return entityManager.merge(user);
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }
}
