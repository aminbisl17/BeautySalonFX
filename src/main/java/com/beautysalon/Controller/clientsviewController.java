package com.beautysalon.Controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.API.ClientCall;
import com.beautysalon.gate.Configuration.ModernAlert;
import com.beautysalon.gate.Model.clients.Client;
import com.beautysalon.gate.Model.clients.ClientHistory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
public class clientsviewController {

    @FXML
    private Button refreshbutton;

    @FXML
    private TableView<Client> table;

    @FXML
    private TextField searchField;

    private final TableView<ClientHistory> historyTable = new TableView<>();

    private boolean serverNotification;

    @FXML
    public void initialize() {

        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );

        table.setPlaceholder(new ProgressIndicator());

        historyTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );

        setupColumns();

             table.setRowFactory(_ -> {
            TableRow<Client> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {

                    SessionManager.setClient(row.getItem());
                    CenterController.loadCenterContent("ClientProfile.fxml");
                }
            });

            return row;
        });
        
        refreshbutton.setOnAction(e -> {
       table.setItems(FXCollections.observableArrayList());
 //      table.setPlaceholder(new ProgressIndicator());
    //   if(SessionManager.getClients() == null) {
        loadClients(); 
    //return;}
    });
    if(SessionManager.getClients() == null){
     loadClients();
     return;
    }
    setupSearch(SessionManager.getClients());
    }

    private void loadClients() {

    ClientCall.fetchClients()
        .thenAccept(success -> {
    
            if (success && SessionManager.getClients() != null) {
               
               serverNotification = true;
            //    stopServerPolling();

                Platform.runLater(() ->
                        setupSearch(SessionManager.getClients()));
            }
        })
        .exceptionally(ex -> {
    
         serverNotification = false;
                    Platform.runLater(() -> {
        ModernAlert.warning(
                "Server unreachable",
                "Failed to fetch client's data!"
        );
    });
      
            return null;
        });
}

    private void setupColumns() {

        TableColumn<Client, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Client, String> emriCol = new TableColumn<>("Emri");
        emriCol.setCellValueFactory(new PropertyValueFactory<>("emri"));

        TableColumn<Client, String> mbiemriCol = new TableColumn<>("Mbiemri");
        mbiemriCol.setCellValueFactory(new PropertyValueFactory<>("mbiemri"));

        TableColumn<Client, String> gjiniaCol = new TableColumn<>("Gjinia");
        gjiniaCol.setCellValueFactory(new PropertyValueFactory<>("gjinia"));

        TableColumn<Client, String> numriCol = new TableColumn<>("Numri Telefonit");
        numriCol.setCellValueFactory(new PropertyValueFactory<>("numri_telefonit"));

        TableColumn<Client, String> dateCol = new TableColumn<>("Data Regjistrimit");
        dateCol.setCellValueFactory(cellData -> {

            if (cellData.getValue().getData_regjistrimit() != null) {

                String formatted = cellData.getValue()
                        .getData_regjistrimit()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                return new javafx.beans.property.SimpleStringProperty(formatted);
            }

            return new javafx.beans.property.SimpleStringProperty("");
        });

        table.getColumns().setAll(List.of(
                idCol, emriCol, mbiemriCol, gjiniaCol, numriCol, dateCol
        ));
    }

    // ================= SEARCH =================
    private void setupSearch(List<Client> clients) {

        if (clients == null) return;

        FilteredList<Client> filteredData =
                new FilteredList<>(
                        FXCollections.observableArrayList(clients),
                        b -> true
                );

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {

            filteredData.setPredicate(client -> {

                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }

                String keyword = newVal.toLowerCase();

                return (client.getEmri() != null &&
                        client.getEmri().toLowerCase().contains(keyword))
                        ||
                        (client.getMbiemri() != null &&
                                client.getMbiemri().toLowerCase().contains(keyword));
            });
        });

        SortedList<Client> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
       if(!sortedData.isEmpty()){
        table.setItems(sortedData);
        return;
       }
          table.setPlaceholder(new Label("Nuk ka klient të regjistruar!"));
        table.setItems(FXCollections.observableArrayList());
    }
}