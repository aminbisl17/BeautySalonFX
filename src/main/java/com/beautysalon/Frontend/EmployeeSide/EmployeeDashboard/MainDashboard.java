package com.beautysalon.Frontend.EmployeeSide.EmployeeDashboard;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class MainDashboard extends BorderPane{
    public MainDashboard(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EmployeeDashboard.fxml"));     
          fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();          
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
