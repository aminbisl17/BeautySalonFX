package com.beautysalon.Backend.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EmployeeDashboardController {

    @FXML
    private Button clientView;

    @FXML
    private Button closebtn;

    @FXML
    private Button minimizebtn;

    @FXML
    private Label pageTitle;

    @FXML
    private Button resizebtn;

    @FXML
    private StackPane spED;


    @FXML
    private void close(){
        ((Stage) closebtn.getScene().getWindow()).close();
    }
}
