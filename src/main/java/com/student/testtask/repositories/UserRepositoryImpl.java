package com.student.testtask.repositories;

import com.student.testtask.dbentities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

@Component
public class UserRepositoryImpl implements UserRepository, TableOperations {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    RowMapper<User> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        return user;
    };

    public void createTable()
    {
        jdbcTemplate.execute("create table user (id varchar(36) primary key, login varchar(255) not null, password varchar(255) not null unique)");
    }

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from user", ROW_MAPPER);
    }

    @Override
    public User getUserById(String id) {
        String SQL = "select * from user where id = ?";
        User user = jdbcTemplate.queryForObject(SQL, new Object[]{id}, ROW_MAPPER);
        return user;
    }

    @Override
    public User save(User user) {
        /*if (user.getId() == null) {
            user.setId(UUID.randomUUID().toString());

            assert jdbcTemplate.update("insert into user values (?, ?, ?)", user.getId(), user.getLogin(), user.getPassword()) > 0;
        } else {
            assert jdbcTemplate.update("update user set login = ?2, password = ?3 where id = ?1", user.getId(), user.getLogin(), user.getPassword()) > 0;
        }*/
        jdbcTemplate.update("insert into user values (?, ?, ?)", user.getId(), user.getLogin(), user.getPassword());

        return getUserById(user.getId());
    }

    @Override
    public int delete(String id) {
        return jdbcTemplate.update("delete from user where id = ?", id);
    }
}
