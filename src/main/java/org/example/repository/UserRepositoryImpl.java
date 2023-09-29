package org.example.repository;

import org.example.dao.UserRepository;
import org.example.database.DatabaseQueryExecutor;
import org.example.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryImpl implements UserRepository {
    private DatabaseQueryExecutor queryExecutor;
    public UserRepositoryImpl() {
        queryExecutor = new DatabaseQueryExecutor();
    }

    public boolean authenticateUser(User user) throws SQLException {

        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        ResultSet resultSet = queryExecutor.executeQuery(query, user.getUserName(), user.getPassword());

        return resultSet.next();

    }
}
