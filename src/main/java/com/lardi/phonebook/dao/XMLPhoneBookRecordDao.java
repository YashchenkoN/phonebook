package com.lardi.phonebook.dao;

import com.lardi.phonebook.common.FileProfile;
import com.lardi.phonebook.entity.PhoneBookRecord;
import com.lardi.phonebook.entity.PhoneBookRecordWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Nikolay Yashchenko
 */
@Repository
@FileProfile
public class XMLPhoneBookRecordDao implements PhoneBookRecordDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(XMLUserDao.class);

    private Map<Long, PhoneBookRecord> inMemoryCache = new HashMap<>();
    private Long sequence = 0L;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public XMLPhoneBookRecordDao() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(PhoneBookRecordWrapper.class);
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            unmarshaller = jaxbContext.createUnmarshaller();
            File file = new File("records.xml");
            List<PhoneBookRecord> records = ((PhoneBookRecordWrapper) unmarshaller.unmarshal(file)).getRecords();
            inMemoryCache = records.stream().collect(Collectors.toMap(PhoneBookRecord::getId, Function.identity()));
            sequence = records.stream().map(PhoneBookRecord::getId).max(Long::compareTo).orElse(0L);
        } catch (JAXBException e) {
            LOGGER.error(e.toString());
        }
    }

    @Override
    public PhoneBookRecord create(PhoneBookRecord phoneBookRecord) {
        phoneBookRecord.setId(sequence = sequence + 1L);
        inMemoryCache.put(phoneBookRecord.getId(), phoneBookRecord);

        try {
            marshaller.marshal(new PhoneBookRecordWrapper(inMemoryCache.values()), new File("records.xml"));
        } catch (JAXBException e) {
            LOGGER.error(e.toString());
            return null;
        }

        return phoneBookRecord;
    }

    @Override
    public PhoneBookRecord read(Long id) {
        return inMemoryCache.get(id);
    }

    @Override
    public PhoneBookRecord read(String mobilePhone) {
        for (PhoneBookRecord phoneBookRecord : inMemoryCache.values()) {
            if (phoneBookRecord != null && phoneBookRecord.getMobilePhone().equals(mobilePhone)) {
                return phoneBookRecord;
            }
        }

        return null;
    }

    @Override
    public PhoneBookRecord update(PhoneBookRecord phoneBookRecord) {
        inMemoryCache.put(phoneBookRecord.getId(), phoneBookRecord);

        try {
            marshaller.marshal(new PhoneBookRecordWrapper(inMemoryCache.values()), new File("records.xml"));
        } catch (JAXBException e) {
            LOGGER.error(e.toString());
            return null;
        }

        return phoneBookRecord;
    }

    @Override
    public void delete(PhoneBookRecord phoneBookRecord) {
        inMemoryCache.remove(phoneBookRecord.getId());
        try {
            marshaller.marshal(new PhoneBookRecordWrapper(inMemoryCache.values()), new File("records.xml"));
        } catch (JAXBException e) {
            LOGGER.error(e.toString());
        }
    }

    @Override
    public List<PhoneBookRecord> getByUserId(Long userId) {
        return inMemoryCache.values().stream()
                .filter(phoneBookRecord -> phoneBookRecord != null && phoneBookRecord.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<PhoneBookRecord> getByFirstName(String firstName, Long userId) {
        return inMemoryCache.values().stream()
                .filter(phoneBookRecord -> phoneBookRecord != null && phoneBookRecord.getUser().getId().equals(userId))
                .filter(phoneBookRecord -> phoneBookRecord.getFirstName().equals(firstName))
                .collect(Collectors.toList());
    }

    @Override
    public List<PhoneBookRecord> getByLastName(String lastName, Long userId) {
        return inMemoryCache.values().stream()
                .filter(phoneBookRecord -> phoneBookRecord != null && phoneBookRecord.getUser().getId().equals(userId))
                .filter(phoneBookRecord -> phoneBookRecord.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }

    @Override
    public List<PhoneBookRecord> getByPhone(String phone, Long userId) {
        return inMemoryCache.values().stream()
                .filter(phoneBookRecord -> phoneBookRecord != null && phoneBookRecord.getUser().getId().equals(userId))
                .filter(phoneBookRecord -> phoneBookRecord.getMobilePhone().equals(phone))
                .collect(Collectors.toList());
    }
}
