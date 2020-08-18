package com.usman.database;

import javafx.scene.control.Alert;

import java.sql.*;

public class Database {
    private static Connection connection = null;
    public final static Connection connection() {
        try {
            connection =  DriverManager.getConnection("jdbc:sqlite:CollegeDatabase.db");
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Database Connection Failed :  "+e.getMessage()).show();
        }
        return connection;
    }

    public static void close(Statement st) throws SQLException {
        connection.close();
        st.close();
    }
    public static void close(Statement st, ResultSet rs) throws SQLException {
        connection.close();
        st.close();
        rs.close();
    }
}
