package com.student.testtask.repositories;

import com.student.testtask.dbentities.User;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

public interface UserRepository {

    void setDataSource(DataSource dataSource);

    List<User> findAll();

    User getUserById(String id);

    User save(User user);

    int delete(String id);
}
