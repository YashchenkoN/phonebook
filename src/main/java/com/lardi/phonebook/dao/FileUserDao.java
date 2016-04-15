package com.lardi.phonebook.dao;

import com.lardi.phonebook.common.UserRecord;
import com.lardi.phonebook.entity.User;
import org.springframework.beans.factory.DisposableBean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Nikolay Yashchenko
 */
public class FileUserDao implements UserDao, DisposableBean {

    private RandomAccessFile file;

    public FileUserDao(String fileName) {
        try {
            file = new RandomAccessFile(fileName, "rw");
        } catch (FileNotFoundException e) {}
    }

    @Override
    public User create(User user) {
        if (read(user.getId()) == null) {
            try {
                file.seek((user.getId() - 1L) * UserRecord.SIZE);
                new UserRecord(user).writeToFile(file);
            } catch (IOException e) {
                e.printStackTrace();
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
        }
        return null;
    }

    @Override
    public User read(String login) {
        // todo implement
        return null;
    }

    @Override
    public User update(User user) {
        if (read(user.getId()) != null) {
            try {
                file.seek((user.getId() - 1L) * UserRecord.SIZE);
                new UserRecord(user).writeToFile(file);
            } catch (IOException e) {
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
