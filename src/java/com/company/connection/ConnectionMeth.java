package com.company.connection;


import com.company.string.StringFile;
import com.company.logger.Loggers;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class ConnectionMeth {
    static Loggers log = new Loggers(com.company.connection.ConnectionMeth.class.getName());
    public static Connection connection = null;
    public static Statement statement = null;
    public static void startConnection() {
        try {
            connection = DriverManager.getConnection(StringFile.DATABASE_URL, StringFile.USER, StringFile.PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static void endConnection() throws SQLException {
        statement.close();
        connection.close();
    }
    public static void startConnectionToMySQL(){};
    public static void endConnectionToMySQL(){};
}