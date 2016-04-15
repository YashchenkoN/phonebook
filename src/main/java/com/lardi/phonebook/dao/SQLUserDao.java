package com.lardi.phonebook.dao;

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
public class SQLUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    private ResultSetExtractor<User> readExtractor = rs -> {
        if (rs.next()) {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setRole(Role.valueOf(rs.getString("role")));
            return user;
        }
        return null;
    };

    public SQLUserDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User create(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", user.getName());
        parameters.put("login", user.getLogin());
        parameters.put("password", user.getPassword());
        parameters.put("role", user.getRole().name());
        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);

        user.setId(key.longValue());

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
