package com.beautysalon.Controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Executors;

import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.API.ClientsAPI;
import com.beautysalon.gate.Configuration.ExecutorConfig;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;
import com.beautysalon.gate.Model.clients.Client;
import com.beautysalon.gate.Model.clients.ClientHistory;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;



public class clientsviewController {

    //@FXML
    //private BorderPane clientsview;

    @FXML
    private Button refreshbutton;

    @FXML
    private TableView<Client> table;

    private TableView<ClientHistory> historyTable = new TableView<>();

    private ClientsAPI clientsService = new ClientsAPI();

    @FXML
private TextField searchField;

    @FXML
    public void initialize(){

        refreshbutton.setOnAction((_) ->{ fetchClientsAsync(); });

         table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

          historyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

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

    table.getColumns().setAll( List.of(idCol, emriCol, mbiemriCol, gjiniaCol, usernameCol, numriCol, dateCol));

    table.setRowFactory((_) -> {
    TableRow<Client> row = new TableRow<>();

    row.setOnMouseClicked(event -> {
        if (!row.isEmpty() && event.getClickCount() == 2) {
              SessionManager.setClient(row.getItem());
              CenterController.loadCenterContent("ClientProfile.fxml");
        }
    });

    return row;
});


   if(SessionManager.getClients() == null){
        fetchClientsAsync();
        return;
   }
  // table.setItems(FXCollections.observableArrayList(SessionManager.getClients()));
  setupSearch(SessionManager.getClients());

    }
    private void fetchClientsAsync() {

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                clientsService.fetchAllClients();
                return null;
            }
        };

        task.setOnSucceeded((_) -> {
    
           // table.setItems(FXCollections.observableArrayList(SessionManager.getClients()));
            setupSearch(SessionManager.getClients());
        });

        task.setOnFailed(event -> {
                APIErrorHandler.handle(task.getException()); 
        });

        ExecutorConfig.submit(task);
    }

    private void setupSearch(List<Client> clients) {

    FilteredList<Client> filteredData =
            new FilteredList<>(
                    FXCollections.observableArrayList(clients),
                    b -> true
            );

    searchField.textProperty().addListener((observable, oldValue, newValue) -> {

        filteredData.setPredicate(client -> {

            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String keyword = newValue.toLowerCase();

            if (client.getEmri() != null &&
                    client.getEmri().toLowerCase().contains(keyword)) {
                return true;
            }

            if (client.getMbiemri() != null &&
                    client.getMbiemri().toLowerCase().contains(keyword)) {
                return true;
            }

            return false;
        });
    });

    SortedList<Client> sortedData = new SortedList<>(filteredData);

    sortedData.comparatorProperty().bind(table.comparatorProperty());

    table.setItems(sortedData);
}
}

