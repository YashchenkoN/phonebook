package com.lardi.phonebook.dao;

import com.lardi.phonebook.common.FileProfile;
import com.lardi.phonebook.entity.User;
import com.lardi.phonebook.entity.UserWrapper;
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
public class XMLUserDao implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(XMLUserDao.class);

    private Map<Long, User> inMemoryCache = new HashMap<>();
    private Long sequence = 0L;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public XMLUserDao() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(UserWrapper.class);
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            unmarshaller = jaxbContext.createUnmarshaller();
            File file = new File("users.xml");
            List<User> users = ((UserWrapper) unmarshaller.unmarshal(file)).getUsers();
            inMemoryCache = users.stream().collect(Collectors.toMap(User::getId, Function.identity()));
            sequence = users.stream().map(User::getId).max(Long::compareTo).orElse(0L);
        } catch (JAXBException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    @Override
    public User create(User user) {
        user.setId(sequence = sequence + 1L);
        inMemoryCache.put(user.getId(), user);

        try {
            marshaller.marshal(new UserWrapper(inMemoryCache.values()), new File("users.xml"));
        } catch (JAXBException e) {
            LOGGER.error(e.toString());
            return null;
        }

        return user;
    }

    @Override
    public User read(Long id) {
        return inMemoryCache.get(id);
    }

    @Override
    public User read(String login) {
        for (User user : inMemoryCache.values()) {
            if (user != null && user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public Long getId(String login) {
        User user = read(login);
        return user != null ? user.getId() : null;
    }

    @Override
    public User update(User user) {
        inMemoryCache.put(user.getId(), user);

        try {
            marshaller.marshal(new UserWrapper(inMemoryCache.values()), new File("users.xml"));
        } catch (JAXBException e) {
            LOGGER.error(e.toString());
            return null;
        }

        return user;
    }

    @Override
    public void delete(User user) {
        inMemoryCache.remove(user.getId());
        try {
            marshaller.marshal(new UserWrapper(inMemoryCache.values()), new File("users.xml"));
        } catch (JAXBException e) {
            LOGGER.error(e.toString());
        }
    }
}
