package com.beautysalon.Backend.Controllers;

import com.beautysalon.Backend.Database.Database;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class registerFormController {
    @FXML
    private TextField emriField;

    @FXML
    private ComboBox<String> gjiniaField;

    @FXML
    private TextField mbiemriField;

    @FXML
    private TextField nrtelField;

    @FXML
    private TextArea pershkrimiField;

    @FXML
    private Button regbtn;

    private EmployeeDashboardController ec;

    public void setEc(EmployeeDashboardController ec) {
        this.ec = ec;
    }

    @FXML
    void initialize() {
        gjiniaField.getItems().addAll("Mashkull", "Femer", "Asnjejes");
        gjiniaField.getSelectionModel().selectFirst();
    }

    @FXML
    void registerClient() {
        Database.RegisterClient(emriField.getText(), mbiemriField.getText(),
                gjiniaField.getSelectionModel().getSelectedItem().charAt(0), nrtelField.getText(),
                pershkrimiField.getText());
        if (ec != null) {
            ec.loadData();
        }
    }
}
