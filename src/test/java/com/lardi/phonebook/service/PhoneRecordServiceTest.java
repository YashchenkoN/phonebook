package com.lardi.phonebook.service;

import com.lardi.phonebook.config.ApplicationConfig;
import com.lardi.phonebook.config.TestDBConfig;
import com.lardi.phonebook.entity.PhoneBookRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author Nikolay Yashchenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, TestDBConfig.class})
public class PhoneRecordServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PhoneBookRecordService phoneBookRecordService;

    @Test
    public void testCreate() {
        PhoneBookRecord phoneBookRecord = new PhoneBookRecord();
        phoneBookRecord.setUser(userService.read(1L));
        phoneBookRecord.setAddress("address");
        phoneBookRecord.setEmail("email@email.com");
        phoneBookRecord.setMobilePhone("+380(44)4444444");
        phoneBookRecord.setFirstName("First");
        phoneBookRecord.setLastName("Last");
        phoneBookRecord.setPatronymic("Patronymic");
        phoneBookRecord.setHomePhone("+380(55)5555555");

        phoneBookRecord = phoneBookRecordService.create(phoneBookRecord);
        assertNotNull(phoneBookRecord);
        assertNotNull(phoneBookRecord.getId());

        assertThat(phoneBookRecord.getAddress(), equalTo("address"));
        assertThat(phoneBookRecord.getEmail(), equalTo("email@email.com"));
        assertThat(phoneBookRecord.getMobilePhone(), equalTo("+380(44)4444444"));
        assertThat(phoneBookRecord.getFirstName(), equalTo("First"));
        assertThat(phoneBookRecord.getLastName(), equalTo("Last"));
        assertThat(phoneBookRecord.getPatronymic(), equalTo("Patronymic"));
        assertThat(phoneBookRecord.getHomePhone(), equalTo("+380(55)5555555"));
        assertThat(phoneBookRecord.getUser(), notNullValue());
        assertThat(phoneBookRecord.getUser().getId(), equalTo(1L));
    }

    @Test
    public void testRead() {
        PhoneBookRecord phoneBookRecord = phoneBookRecordService.read(1L);
        assertNotNull(phoneBookRecord);
        assertNotNull(phoneBookRecord.getId());

        assertThat(phoneBookRecord.getAddress(), equalTo("address"));
        assertThat(phoneBookRecord.getEmail(), equalTo("email@email.com"));
        assertThat(phoneBookRecord.getMobilePhone(), equalTo("+380(00)0000000"));
        assertThat(phoneBookRecord.getFirstName(), equalTo("First"));
        assertThat(phoneBookRecord.getLastName(), equalTo("Last"));
        assertThat(phoneBookRecord.getPatronymic(), equalTo("Patronymic"));
        assertThat(phoneBookRecord.getHomePhone(), equalTo("+380(55)5555555"));
        assertThat(phoneBookRecord.getUser(), notNullValue());
        assertThat(phoneBookRecord.getUser().getId(), equalTo(1L));
    }

    @Test
    public void testUpdate() {
        PhoneBookRecord phoneBookRecord = phoneBookRecordService.read(2L);
        assertNotNull(phoneBookRecord);
        assertNotNull(phoneBookRecord.getId());

        assertThat(phoneBookRecord.getAddress(), equalTo("address"));
        assertThat(phoneBookRecord.getEmail(), equalTo("email@email.com"));
        assertThat(phoneBookRecord.getMobilePhone(), equalTo("+380(00)0000001"));
        assertThat(phoneBookRecord.getFirstName(), equalTo("First"));
        assertThat(phoneBookRecord.getLastName(), equalTo("Last"));
        assertThat(phoneBookRecord.getPatronymic(), equalTo("Patronymic"));
        assertThat(phoneBookRecord.getHomePhone(), equalTo("+380(55)5555555"));
        assertThat(phoneBookRecord.getUser(), notNullValue());
        assertThat(phoneBookRecord.getUser().getId(), equalTo(1L));

        phoneBookRecord.setAddress("address2");
        phoneBookRecordService.update(phoneBookRecord);
        phoneBookRecord = phoneBookRecordService.read(2L);

        assertThat(phoneBookRecord.getAddress(), equalTo("address2"));
        assertThat(phoneBookRecord.getEmail(), equalTo("email@email.com"));
        assertThat(phoneBookRecord.getMobilePhone(), equalTo("+380(00)0000001"));
        assertThat(phoneBookRecord.getFirstName(), equalTo("First"));
        assertThat(phoneBookRecord.getLastName(), equalTo("Last"));
        assertThat(phoneBookRecord.getPatronymic(), equalTo("Patronymic"));
        assertThat(phoneBookRecord.getHomePhone(), equalTo("+380(55)5555555"));
        assertThat(phoneBookRecord.getUser(), notNullValue());
        assertThat(phoneBookRecord.getUser().getId(), equalTo(1L));
    }

    @Test
    public void testReadByUserId() {
        List<PhoneBookRecord> result = phoneBookRecordService.getByUserId(1L);
        assertThat(result.size(), equalTo(3));
    }

    @Test
    public void testDelete() {
        PhoneBookRecord phoneBookRecord = phoneBookRecordService.read(3L);

        phoneBookRecordService.delete(phoneBookRecord.getId());
        phoneBookRecord = phoneBookRecordService.read(3L);
        assertThat(phoneBookRecord, nullValue());
    }
}
