package com.da_thao.project_test.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.sql.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DatabaseConnection {
    static String jdbcURL = "jdbc:mysql://127.0.0.1:3306/spring_boot_freamwork?useSSL=false&serverTimezone=UTC";
    static String jdbcUsername = "root";
    static String jdbcPassword = "";
    static Connection connection;

    /**
     * Phương thức getConnection để lấy kết nối tới cơ sở dữ liệu.
     * Phương thức này kiểm tra nếu connection chưa được khởi tạo (connection == null) thì sẽ tạo một kết nối mới.
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Tải driver JDBC cho MySQL vào bộ nhớ, điều này cần thiết để có thể kết nối.
                Class.forName("com.mysql.jdbc.Driver");
                // Khởi tạo kết nối tới cơ sở dữ liệu với các thông tin đã cung cấp (URL, username, password).
                connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * Phương thức closeConnection để đóng kết nối tới cơ sở dữ liệu khi không cần sử dụng nữa.
     */
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            connection = null;
        }
    }

    //for select
    public static ResultSet executeQuery(String query, Object... parameters) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    //for any change
    public static Integer executeUpdate(String query, Object... parameters) {
        Integer generatedId = -1;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

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