package com.beautysalon;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageManager {

    private static Stage ps;

    public static void init(Stage stage) {
        ps = stage;
        ps.setOnCloseRequest(e -> {
         Platform.exit();
           System.exit(0);
        });
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
     //   ps.initStyle(StageStyle.UNDECORATED);
     //  createStage("Kyçu", "/fxml/login.fxml", false);
       Stage loginStage = new Stage();
    loginStage.initStyle(StageStyle.UNDECORATED);

    try {
        loginStage.setScene(new Scene(FXMLLoader.load(StageManager.class.getResource("/fxml/login.fxml"))));
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    loginStage.show();
    }

    public static void MainWindow(){ 
        ps.initStyle(StageStyle.DECORATED);
        createStage("Ballina", "/fxml/MainWindow.fxml", true);
     }

public static Stage getStage() {
        return ps;
    }
}
