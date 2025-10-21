package com.beautysalon.Backend.Database;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class Database {
     //private static Database instance;
     private static Connection connection;
     private static PreparedStatement ps;
     private static ResultSet rs;
     private static Statement st;

   
     public static void connect() {
        if (connection != null) return; // already connected

        try {
            System.out.println("Connecting to SQL Server...");
            connection = DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;instanceName=SQLEXPRESS;databaseName=beautysalon;integratedSecurity=true;encrypt=true;trustServerCertificate=true;"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     public static Alert showAlert(Alert.AlertType type, String title, String header, String content) {
          Alert alert = new Alert(type);
          alert.setTitle(title);
          alert.setHeaderText(header);
          alert.setContentText(content);
          alert.showAndWait();
          return alert;
     }

   

     public static void main(String[] args) {
       connect();
     }
}