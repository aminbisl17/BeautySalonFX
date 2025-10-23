package com.beautysalon.Backend.Controllers;

import java.util.List;

import com.beautysalon.Backend.Database.Database;
import com.beautysalon.Backend.Database.Entities.clientsData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EmployeeDashboardController {

    @FXML
    private BorderPane clientTable;

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
    private TableView<clientsData> table;

    @FXML
    private TableColumn<clientsData, Integer> colID;

    @FXML
    private TableColumn<clientsData, String> colEmri;

    @FXML
    private TableColumn<clientsData, String> colMbiemri;

    @FXML
    private TableColumn<clientsData, String> colNumri;

    @FXML
    private TableColumn<clientsData, String> colPershkrimi;

    @FXML
    private TableColumn<clientsData, Character> colGjinia;

    @FXML
    private TableColumn<clientsData, java.sql.Timestamp> colData;

    @FXML
    public void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colEmri.setCellValueFactory(new PropertyValueFactory<>("emri"));
        colMbiemri.setCellValueFactory(new PropertyValueFactory<>("mbiemri"));
        colNumri.setCellValueFactory(new PropertyValueFactory<>("numri_telefonit"));
        colPershkrimi.setCellValueFactory(new PropertyValueFactory<>("pershkrimi"));
        colGjinia.setCellValueFactory(new PropertyValueFactory<>("gjinia"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data_regjistrimit"));

        List<clientsData> list = Database.getAllClients();

    // Convert to ObservableList
    ObservableList<clientsData> data = FXCollections.observableArrayList(list);
    table.setItems(data);
    }

    @FXML
    void close(ActionEvent 
    event) {
((Stage) closebtn.getScene().getWindow()).close();
    }

}
