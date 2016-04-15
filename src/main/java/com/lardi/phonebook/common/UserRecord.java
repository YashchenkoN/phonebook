package com.lardi.phonebook.common;

import com.lardi.phonebook.entity.User;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Nikolay Yashchenko
 */
public class UserRecord extends User {
    public static final int SIZE = Long.BYTES + (3 * (Character.BYTES * 15));

    public UserRecord() {}

    public UserRecord(User user) {
        setId(user.getId());
        setLogin(user.getLogin());
        setPassword(user.getPassword());
        setName(user.getName());
    }

    public void readFromFile(RandomAccessFile file) throws IOException {
        setId(file.readLong());
        setName(readString(file));
        setLogin(readString(file));
        setPassword(readString(file));
    }

    public void writeToFile(RandomAccessFile file) throws IOException {
        file.writeLong(getId());
        writeString(file, getName());
        writeString(file, getLogin());
        writeString(file, getPassword());
    }

    private String readString(RandomAccessFile file) throws IOException {
        char[] s = new char[15];
        for (int i = 0; i < s.length; i++)
            s[i] = file.readChar();
        return new String(s).replace('\0', ' ');
    }

    private void writeString(RandomAccessFile file, String s) throws IOException {
        StringBuffer buffer;
        if (s != null) {
            buffer = new StringBuffer(s);
        } else {
            buffer = new StringBuffer(15);
        }
        buffer.setLength(15);
        file.writeChars(buffer.toString());
    }
}
