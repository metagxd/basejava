package com.basejava.webapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public void process(ConnectionFactory connectionFactory, String sql, Statement statement) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            statement.execute(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    public interface Statement {
        void execute(PreparedStatement preparedStatement) throws SQLException;
    }

}
