package com.lardi.phonebook.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikolay Yashchenko
 */
public class PhoneBook {
    private Long id;
    private User owner;
    private List<PhoneBookRecord> phoneBookRecords;

    public PhoneBook() {
        phoneBookRecords = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<PhoneBookRecord> getPhoneBookRecords() {
        return phoneBookRecords;
    }

    public void setPhoneBookRecords(List<PhoneBookRecord> phoneBookRecords) {
        this.phoneBookRecords = phoneBookRecords;
    }

    public void addPhoneRecord(PhoneBookRecord phoneBookRecord) {
        phoneBookRecords.add(phoneBookRecord);
    }
}
