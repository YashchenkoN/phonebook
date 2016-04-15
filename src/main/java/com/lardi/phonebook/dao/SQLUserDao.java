package com.lardi.phonebook.dao;

import com.lardi.phonebook.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;

/**
 * @author Nikolay Yashchenko
 */
public class SQLUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    private ResultSetExtractor<User> readExtractor = rs -> {
        if (rs.next()) {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            return user;
        }
        return null;
    };

    public SQLUserDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User create(User user) {
        String sql = "INSERT INTO users (name, login, password) VALUES (?, ?, ?, ?)";
//        jdbcTemplate.update(sql, user.getName(), user.getLogin(), user.getPassword());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getPassword());
            return ps;
        }, keyHolder);

        user.setId(keyHolder.getKey().longValue());

        return user;
    }

    @Override
    public User read(Long id) {
        String sql = "SELECT * FROM users where id = ?";
        return jdbcTemplate.query(sql, new Object[] { id }, readExtractor);
    }

    @Override
    public User read(String login) {
        String sql = "SELECT * FROM users where login = ?";
        return jdbcTemplate.query(sql, new Object[] { login }, readExtractor);
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE users SET name = ?, login = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getLogin(), user.getPassword(), user.getId());
        return user;
    }

    @Override
    public void delete(User user) {
        String sql = "DELETE FROM users where id = ?";
        jdbcTemplate.update(sql, user.getId());
    }
}
