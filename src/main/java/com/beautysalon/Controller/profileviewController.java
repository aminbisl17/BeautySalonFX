package com.beautysalon.Controller;

import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.Model.User;

import javafx.fxml.FXML;
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
    private void initialize(){

        User user = SessionManager.getUser();

        emriField.setText(user.getEmri());
        mbiemriField.setText(user.getMbiemri());
        numriTelField.setText(user.getNumri_telefonit());
        emailField.setText(user.getEmail());

    }
}
