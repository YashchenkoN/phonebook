package com.lardi.phonebook.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Nikolay Yashchenko
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWrapper {
    private List<User> users = new ArrayList<>();

    public UserWrapper() {
    }

    public UserWrapper(Collection<User> users) {
        this.users.addAll(users);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
