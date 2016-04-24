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
public class PhoneBookRecordWrapper {
    private List<PhoneBookRecord> records = new ArrayList<>();

    public PhoneBookRecordWrapper() {
    }

    public PhoneBookRecordWrapper(Collection<PhoneBookRecord> collection) {
        records.addAll(collection);
    }

    public List<PhoneBookRecord> getRecords() {
        return records;
    }

    public void setRecords(List<PhoneBookRecord> records) {
        this.records = records;
    }
}
