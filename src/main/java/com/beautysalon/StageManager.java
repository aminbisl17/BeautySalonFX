package com.beautysalon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class StageManager {

    private static Stage ps;

    public static void init(Stage stage) {
        ps = stage;
    }
    
    public static void login(){

        try{

            Parent root = FXMLLoader.load(StageManager.class.getResource("/fxml/login.fxml"));

            ps.setTitle("login");
            ps.setScene(new Scene(root));
            ps.show();
        
        }catch(IOException e){
               Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Loading Error");
            alert.setHeaderText("Unable to load Login screen");
            alert.setContentText("The login screen could not be loaded.\n"
                               + "Please contact support.");

            alert.showAndWait();
            e.printStackTrace();
        }
    }
}
