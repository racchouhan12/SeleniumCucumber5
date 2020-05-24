package com.automation.utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.automation.exceptions.SqlOperationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBUtils {
    private static Logger logger = LogManager.getLogger(DBUtils.class);

    public Connection getConnection(final String jdbcDriver, final String username, final String password,
                                    final String jdbcUrl) throws SqlOperationException {
        // Instantiate driver instance.
        try {
            Class.forName(jdbcDriver).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {

            throw new SqlOperationException("Unable to get driver instance.", e);
        }
        // Connect to the database
        try (Connection databaseConnection = java.sql.DriverManager.getConnection(jdbcUrl, username, password)) {
            logger.info("JDBC driver loaded");
            logger.info("Connected to the database");
            return java.sql.DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            throw new SqlOperationException("Unable to connect to Sql Server.", e);
        }
    }

    public ResultSet executeSqlQuery(final Statement stmt, final String sqlQuery) throws SqlOperationException {
        ResultSet rSet = null;
        try {
            rSet = stmt.executeQuery(sqlQuery);
        } catch (final SQLException e) {
            throw new SqlOperationException("Failed to execute sql query.", e);
        }
        return rSet;
    }

    public void executeSqlUpdateQuery(final Statement stmt, final String sqlQuery) throws SqlOperationException {
        try {
            stmt.executeUpdate(sqlQuery);
        } catch (final SQLException e) {
            throw new SqlOperationException("Failed to update sql query.", e);
        }
    }

    public Statement executeSqlStatement(final Connection connection) throws SqlOperationException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (final SQLException e) {
            throw new SqlOperationException("Failed to execute sql statement.", e);
        }
        return statement;
    }

    public void closeConnection(final Connection connection) throws SqlOperationException {
        try {
            connection.close();
        } catch (final SQLException e) {
            throw new SqlOperationException("Failed to close sql connection.", e);
        }
    }
}
