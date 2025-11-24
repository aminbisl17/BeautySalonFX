package com.beautysalon;

import java.io.IOException;

import com.beautysalon.Backend.Database.Database;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
         Database.connect();

        stage.initStyle(StageStyle.UNDECORATED);  
    
        Parent root = new FXMLLoader(getClass().getResource("/fxml/EmployeeDashboard.fxml")).load();
        
        stage.setScene(new Scene(root, 1001, 653));
        stage.setTitle("BeautySalonFX");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
