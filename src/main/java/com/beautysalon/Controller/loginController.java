package com.beautysalon.Controller;

import javax.naming.AuthenticationException;

import com.beautysalon.StageManager;
import com.beautysalon.gate.API.Authentication.AuthService;
import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.Exceptions.ServerErrorException;
import com.beautysalon.gate.Model.User;
import com.beautysalon.gate.responses.loginResponse;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

            ((Stage) submitButton.getScene().getWindow()).close();
            StageManager.MainWindow();

        } 
        catch (ServerErrorException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error 500");
        alert.setHeaderText("Server unreachable");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
         catch (AuthenticationException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login failed");
        alert.setHeaderText("Invalid credentials");
        alert.setContentText("Username or password is incorrect.");
        alert.showAndWait();
    }
        catch(RuntimeException e){
         Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Connection error");
        alert.setHeaderText("Server unreachable!");
        alert.setContentText("Please check your internet connection.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
        }
        catch (Exception e) {
        e.printStackTrace();
         Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Unexpected error");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
        }
    }
}
