package com.basejava.webapp.sql;

import com.basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String bdPassword) {
        try {
            Class.forName("org.postgresql.Driver");
            this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, bdPassword);
        } catch (ClassNotFoundException e) {
            throw new StorageException("Sql connection error", e);
        }
    }

    public <T> T process(String sql, Statement<T> executor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return executor.execute(preparedStatement);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T result = executor.execute(connection);
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    @FunctionalInterface
    public interface Statement<T> {
        T execute(PreparedStatement preparedStatement) throws SQLException;
    }

    @FunctionalInterface
    public interface SqlTransaction<T> {
        T execute(Connection connection) throws SQLException;
    }

}
