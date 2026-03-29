package com.beautysalon.Controller;


import com.beautysalon.StageManager;
import com.beautysalon.gate.API.AuthAPI;
import com.beautysalon.gate.API.UserAPI;
import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class loginController{

    @FXML
    private Button submitButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField userpasswordField;

    private AuthAPI authService = new AuthAPI();
    private UserAPI userService = new UserAPI(); 

    @FXML
    public void initialize() {
       
        submitButton.setOnAction(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = userpasswordField.getText();

          if(username.isEmpty() || password.isEmpty()){
                return;
            }

        try {

               Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                authService.login(username, password);
                userService.getUserData();
                return null;
            }
        };

        task.setOnSucceeded((_) -> {
    
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Welcome");
            alert.setHeaderText("Welcome " + SessionManager.getUser().getEmri());
            alert.showAndWait();

            StageManager.getStage().close();
            StageManager.MainWindow();
        });

        task.setOnFailed(event -> {
                APIErrorHandler.handle(task.getException()); 
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

        } catch(Exception e){
            APIErrorHandler.handle(e);
        }
    }
}
