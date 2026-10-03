package com.employee;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {

        Connection con = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/employee_management";
            String username = System.getenv("DB_USERNAME");
            String password = System.getenv("DB_PASSWORD");

            con = DriverManager.getConnection(
                    url,
                    username,
                    password
            );

            System.out.println(
                    "Database Connected Successfully!"
            );

            System.out.println(
                    "Connected Database: " +
                            con.getCatalog()
            );

        } catch (Exception e) {

            e.printStackTrace();
        }

        return con;
    }

    public static void main(String[] args) {

        getConnection();
    }
}