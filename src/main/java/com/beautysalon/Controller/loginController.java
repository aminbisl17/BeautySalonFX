package com.beautysalon.Controller;

import com.beautysalon.StageManager;
import com.beautysalon.API.Authentication.AuthService;
import com.beautysalon.API.responses.loginResponse;
import com.beautysalon.Model.User;

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
 private AuthService authService = new AuthService(); // create instance

    @FXML
    public void initialize() {
       
        submitButton.setOnAction(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = userpasswordField.getText();

        try {

            loginResponse response = authService.login(username, password);

 
            String token = response.getToken();
            User user = response.getUser();

           Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Welcome");
            alert.setHeaderText("Welcome " + user.getEmri());
         //   alert.setContentText(ex.getMessage());
            alert.showAndWait();

           

        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Unable to login");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
}
