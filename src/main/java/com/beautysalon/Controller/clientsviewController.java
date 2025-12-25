package com.beautysalon.Controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.beautysalon.gate.API.Clients.ClientsService;
import com.beautysalon.gate.Model.clients.Client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class clientsviewController {

    @FXML
    private BorderPane clientsview;

    @FXML
    private Button registerbutton;

    @FXML
    private TableView<Client> table;

    private ClientsService clientsService = new ClientsService();

    @FXML
    public void initialize(){

         table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

            TableColumn<Client, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Client, String> emriCol = new TableColumn<>("Emri");
        emriCol.setCellValueFactory(new PropertyValueFactory<>("emri"));

        TableColumn<Client, String> mbiemriCol = new TableColumn<>("Mbiemri");
        mbiemriCol.setCellValueFactory(new PropertyValueFactory<>("mbiemri"));

        TableColumn<Client, String> gjiniaCol = new TableColumn<>("Gjinia");
        gjiniaCol.setCellValueFactory(new PropertyValueFactory<>("gjinia"));

        TableColumn<Client, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Client, String> numriCol = new TableColumn<>("Numri Telefonit");
        numriCol.setCellValueFactory(new PropertyValueFactory<>("numri_telefonit"));

        TableColumn<Client, String> dateCol = new TableColumn<>("Data Regjistrimit");
        dateCol.setCellValueFactory(cellData -> {
            if (cellData.getValue().getData_regjistrimit() != null) {
                String formatted = cellData.getValue()
                        .getData_regjistrimit()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                return new javafx.beans.property.SimpleStringProperty(formatted);
            } else {
                return new javafx.beans.property.SimpleStringProperty("");
            }
        });

    table.getColumns().addAll(idCol, emriCol, mbiemriCol, gjiniaCol, usernameCol, numriCol, dateCol);
    fetchClientsAsync();
    }
    private void fetchClientsAsync() {
        Task<List<Client>> task = new Task<>() {
            @Override
            protected List<Client> call() throws Exception {
                // This will run in a background thread
                return clientsService.fetchAllClients();
            }
        };

        task.setOnSucceeded(event -> {
            List<Client> clientsList = task.getValue();
            table.setItems(FXCollections.observableArrayList(clientsList));
        });

        task.setOnFailed(event -> {
            Throwable ex = task.getException();
            ex.printStackTrace();
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error fetching clients");
                alert.setHeaderText(ex.getClass().getSimpleName());
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            });
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
