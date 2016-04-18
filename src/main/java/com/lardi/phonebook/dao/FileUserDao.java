package com.lardi.phonebook.dao;

import com.lardi.phonebook.common.UserRecord;
import com.lardi.phonebook.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Nikolay Yashchenko
 */
@com.lardi.phonebook.common.File
@Repository
public class FileUserDao implements UserDao, DisposableBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUserDao.class);

    private RandomAccessFile file;
    private Long maxId;

    public FileUserDao() {
        try {
            File f = new File("users.csv");
            if (f.exists()) {
                f.delete();
            }
            file = new RandomAccessFile("users.csv", "rw");
            maxId = file.length() / UserRecord.SIZE;
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    @Override
    public User create(User user) {
        maxId = maxId + 1L;
        user.setId(maxId);
        if (read(user.getId()) == null) {
            try {
                file.seek((user.getId() - 1L) * UserRecord.SIZE);
                new UserRecord(user).writeToFile(file);
            } catch (IOException e) {
                maxId = maxId - 1L;
                LOGGER.error(e.getLocalizedMessage());
                return null;
            }
        }
        return user;
    }

    @Override
    public User read(Long id) {
        try {
            UserRecord record = new UserRecord();
            file.seek((id - 1L) * UserRecord.SIZE);
            record.readFromFile(file);
            return record;
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public User read(String login) {
        // todo implement
        return null;
    }

    @Override
    public Long getId(String login) {
        return null;
    }

    @Override
    public User update(User user) {
        if (read(user.getId()) != null) {
            try {
                file.seek((user.getId() - 1L) * UserRecord.SIZE);
                new UserRecord(user).writeToFile(file);
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage());
            }
        }

        return user;
    }

    @Override
    public void delete(User user) {
        if (read(user.getId()) != null) {
            try {
                file.seek((user.getId() - 1L) * UserRecord.SIZE);
                UserRecord record = new UserRecord();
                record.writeToFile(file);
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage());
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        if (file != null) {
            file.close();
        }
    }

    //    public void showAllRecords() {
//        PersonRecord record = new PersonRecord();
//        try {
//            file.seek(0);
//            while (true) {
//                do {
//                    record.readFromFile(file);
//                } while (record.getId() == 0);
//                System.out.println(record.toString());
//            }
//        } catch (EOFException ex1) {
//            return;
//        } catch (IOException ex2) {
//            System.err.println("error reading file");
//        }
//    }
}
