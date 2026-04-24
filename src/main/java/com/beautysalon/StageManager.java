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
    
    private static void createStage(String title, String FXMLSourcePath, boolean border){

      try{

            Parent root = FXMLLoader.load(StageManager.class.getResource(FXMLSourcePath));

            ps.setTitle(title);
            ps.setScene(new Scene(root));
            ps.setMaximized(border);
            ps.setResizable(border);
            ps.show();
        
        }catch(IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Loading Error");
            alert.setHeaderText("Unable to load " + title +  " screen");
            alert.setContentText("The  " + title +  " screen could not be loaded.\n"
                               + "Please contact support.");

            alert.showAndWait();
            e.printStackTrace();
        }

    }
    public static void login(){
       createStage("login", "/fxml/login.fxml", false);
    }

    public static void MainWindow(){ 
        createStage("login", "/fxml/MainWindow.fxml", true);
     }

public static Stage getStage() {
        return ps;
    }
}
