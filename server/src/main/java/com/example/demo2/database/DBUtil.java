package com.example.demo2.database;


import java.sql.*;

public class DBUtil {
    // table
    private static final String TABLE_ACCOUNT = "account_info";
    private static final String TABLE_COURSE = "course_info";
    private static final String TABLE_ASSOCIATION = "association_table";

    // connect to MySql database
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/from_navicat"; // 数据库的Url
        Connection connecter = null;
        try {//com.mysql.cj.jdbc.Driver
//            connecter = DriverManager.getConnection(url +
//                    "useSSL=false", "root", "12345");
            Class.forName("com.mysql.cj.jdbc.Driver"); // java反射，固定写法
            connecter = (Connection) DriverManager.getConnection(url, "root", "12345");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connecter;
    }
    public static String getAccountTableName(){
        return TABLE_ACCOUNT;
    }
    public static String getCourseTableName(){
        return TABLE_COURSE;
    }
    public static String getAssociationTableName(){
        return TABLE_ASSOCIATION;
    }
    public static void close(Connection connection) throws SQLException {
        close(connection, null, null);
    }

    public static void close(Connection connection, Statement statement) throws SQLException {
        close(connection, statement, null);
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        if (connection != null) {
            connection.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (resultSet != null) {
            resultSet.close();
        }
    }

}