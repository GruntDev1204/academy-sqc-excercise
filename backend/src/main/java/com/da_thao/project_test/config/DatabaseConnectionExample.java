package com.da_thao.project_test.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.sql.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DatabaseConnectionExample {
    static String jdbcURL = "your-link";
    static String jdbcUsername = "";
    static String jdbcPassword = "";
    static Connection connection;


    /**
     * closeConnection - this method to get the connection to database server
     * check if  connection not exist (connection == null) , then it make the new connect.
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");

                connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * closeConnection - this method to close the connection
     */
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            connection = null;
        }
    }


    //methods using in repository
    //for use select query
    public static ResultSet executeQuery(String sql, Object... parameters) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    //for any change (delete , set , create)
    public static Integer executeUpdate(String sql, Object... parameters) {
        Integer generatedId = -1;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedId;
    }

}