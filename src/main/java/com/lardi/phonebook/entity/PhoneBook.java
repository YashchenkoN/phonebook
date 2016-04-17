package com.lardi.phonebook.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikolay Yashchenko
 */
@Entity
@Table(name = "phone_book")
public class PhoneBook {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User owner;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "phone_book_id")
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
