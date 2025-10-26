package com.beautysalon.Backend.Database;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.beautysalon.Backend.Database.Entities.clientsData;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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

        public static List<clientsData> getAllClients() {

        try {

            List<clientsData> list = new ArrayList<>();
            rs = (connection.createStatement()).executeQuery("SELECT * FROM beautysalon.clients");

            while (rs.next()) {
                list.add(new clientsData
                       (rs.getInt("ID"),
                        rs.getString("emri"),
                        rs.getString("mbiemri"),
                        rs.getString("gjinia").charAt(0),
                        rs.getString("numri_telefonit"),
                        rs.getTimestamp("data_regjistrimit"),
                        rs.getString("pershkrimi")));
            }
            rs = null;

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
   
      public static void RegisterClient(String emri, String mbiemri, char gjinia, String numri_telefonit, String pershkrimi) {

        for (clientsData data : getAllClients()) {
            if ((data.getEmri().equalsIgnoreCase(emri) && data.getMbiemri().equalsIgnoreCase(mbiemri)) ||
                    data.getNumri_telefonit().equals(numri_telefonit)) {
                JOptionPane.showMessageDialog(null, "User already exists!", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        try {

            ps = connection.prepareStatement(
                    "INSERT INTO beautysalon.clients(emri, mbiemri, gjinia, numri_telefonit, pershkrimi) VALUES(?,?,?,?,?)");
           // ps.setInt(1, ID);
            ps.setString(1, emri);
            ps.setString(2, mbiemri);
            ps.setString(3, String.valueOf(gjinia));
            ps.setString(4, numri_telefonit);
            ps.setString(5, pershkrimi);
            ps.executeUpdate();
            ps.close();

          showAlert(AlertType.INFORMATION, "Success", "A client has been registered!", emri + " has been registered!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


     public static void main(String[] args) {
       connect();
       for(clientsData d : getAllClients()){
        System.out.println(d.getEmri());
       }
     }
}