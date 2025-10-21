package com.beautysalon;

import java.io.IOException;

import com.beautysalon.Frontend.EmployeeSide.EmployeeDashboard.MainDashboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
    
        
        Parent root = new FXMLLoader(getClass().getResource("/fxml/EmployeeDashboard.fxml")).load();
        
        stage.setScene(new Scene(root, 1001, 653));
        stage.setTitle("BeautySalonFX");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
