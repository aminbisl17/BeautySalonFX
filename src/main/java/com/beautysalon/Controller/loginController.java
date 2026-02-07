package com.beautysalon.Controller;


import com.beautysalon.StageManager;
import com.beautysalon.gate.API.Authentication.AuthService;
import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;

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

    private AuthService authService = new AuthService(); 

    @FXML
    public void initialize() {
       
        submitButton.setOnAction(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = userpasswordField.getText();

        try {

            if(username.isEmpty() || password.isEmpty()){
                return;
            }

            authService.login(username, password);
         
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Welcome");
            alert.setHeaderText("Welcome " + SessionManager.getUser().getEmri());
            alert.showAndWait();

          //  ((Stage) submitButton.getScene().getWindow()).close();
            StageManager.getStage().close();
            StageManager.MainWindow();

        } catch(Exception e){
            APIErrorHandler.handle(e);
        }
    }
}
