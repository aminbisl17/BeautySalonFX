package com.beautysalon.Controller;

import java.util.Optional;

import com.beautysalon.StageManager;
import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.Model.User;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class profileviewController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField emriField;

    @FXML
    private TextField mbiemriField;

    @FXML
    private TextField numriTelField;

     @FXML
    private Button logoutbtn;

    @FXML
    private void initialize(){

        User user = SessionManager.getUser();

        emriField.setText(user.getEmri());
        mbiemriField.setText(user.getMbiemri());
        numriTelField.setText(user.getNumri_telefonit());
        emailField.setText(user.getEmail());

        logoutbtn.setOnAction((_) ->{ 

              Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Log out");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure you want to log out?");

        Optional<ButtonType> result = alert.showAndWait();
         
         if (result.isPresent() && result.get() == ButtonType.OK) {
            SessionManager.ClearToken();
            StageManager.getStage().close();
            StageManager.login();
         }

        });
    }
}
