package com.beautysalon.Controller;


import java.util.Optional;
import com.beautysalon.StageManager;
import com.beautysalon.gate.Configuration.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class profileviewController {


@FXML
private Label roleField;

@FXML
private Label emriField;

@FXML
private Button logoutbtn;

@FXML
private void initialize() {

    String role = SessionManager.getPrimaryResponse().getRole();

    roleField.getStyleClass().remove("role-admin");
    roleField.getStyleClass().remove("role-employee");

    if ("ROLE_ADMIN".equals(role)) {
        roleField.getStyleClass().add("role-admin");
    } else {
        roleField.getStyleClass().add("role-employee");
    }

    roleField.setText(role.replace("ROLE_", ""));
    emriField.setText(SessionManager.getPrimaryResponse().getUsername());

    logoutbtn.setOnAction(_ -> {
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
