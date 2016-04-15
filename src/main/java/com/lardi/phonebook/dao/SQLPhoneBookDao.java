package com.lardi.phonebook.dao;

import com.lardi.phonebook.entity.PhoneBook;
import com.lardi.phonebook.entity.Role;
import com.lardi.phonebook.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikolay Yashchenko
 */
public class SQLPhoneBookDao implements PhoneBookDao {

    private JdbcTemplate jdbcTemplate;

    private ResultSetExtractor<PhoneBook> readExtractor = rs -> {
        if (rs.next()) {
            PhoneBook phoneBook = new PhoneBook();
            phoneBook.setId(rs.getLong("p.id"));

            User user = new User();
            user.setId(rs.getLong("u.id"));
            user.setName(rs.getString("u.name"));
            user.setLogin(rs.getString("u.login"));
            user.setPassword(rs.getString("u.password"));
            user.setRole(Role.valueOf(rs.getString("u.role")));
            phoneBook.setOwner(user);

            return phoneBook;
        }
        return null;
    };

    public SQLPhoneBookDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public PhoneBook create(PhoneBook phoneBook) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("phone_book")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", phoneBook.getOwner().getId());
        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);

        phoneBook.setId(key.longValue());

        return phoneBook;
    }

    @Override
    public PhoneBook read(Long id) {
        String sql = "SELECT * FROM phone_book p JOIN users ON p.user_id = u.id where p.id = ?";
        return jdbcTemplate.query(sql, new Object[] { id }, readExtractor);
    }

    @Override
    public PhoneBook getByUserId(Long userId) {
        String sql = "SELECT * FROM phone_book p JOIN";
        return null;
    }

    @Override
    public PhoneBook update(PhoneBook phoneBook) {
        return null;
    }

    @Override
    public void delete(PhoneBook phoneBook) {

    }
}
