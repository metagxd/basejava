package com.basejava.webapp.sql;

import com.basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T process(String sql, Statement<T> statement) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return statement.execute(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException("Process error.", e);
        }
    }

    @FunctionalInterface
    public interface Statement<T> {
        T execute(PreparedStatement preparedStatement) throws SQLException;
    }

}
