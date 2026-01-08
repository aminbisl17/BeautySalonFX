package com.beautysalon.Controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.beautysalon.gate.API.Clients.ClientsService;
import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;
import com.beautysalon.gate.Model.clients.Client;
import com.beautysalon.gate.Model.clients.ClientHistory;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class clientsviewController {

    //@FXML
    //private BorderPane clientsview;

    @FXML
    private Button refreshbutton;

    @FXML
    private TableView<Client> table;

    private TableView<ClientHistory> historyTable = new TableView<>();

    private ClientsService clientsService = new ClientsService();

    @FXML
    public void initialize(){

        refreshbutton.setOnAction((_) ->{ table.getItems().clear(); fetchClientsAsync(); });

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
            ClientProfile(row.getItem());
        }
    });

    return row;
});


   if(SessionManager.getClients() == null){
        fetchClientsAsync();
        return;
   }
   table.setItems(FXCollections.observableArrayList(SessionManager.getClients()));

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
    
            table.setItems(FXCollections.observableArrayList(SessionManager.getClients()));
            
        });

        task.setOnFailed(event -> {
                APIErrorHandler.handle(task.getException()); 
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void fetchClientHistory(Client client){
      Task<List<ClientHistory>> task = new Task<>(){

        @Override
        protected List<ClientHistory> call() throws Exception {
           return clientsService.getClientHistory(client.getID());
        }
      };

         task.setOnSucceeded((_)->{
            
                List<ClientHistory> history = task.getValue();
                client.setClientHistory(history);
                historyTable.setItems(FXCollections.observableArrayList(history));
        });

           task.setOnFailed((_)->{
        /*      Throwable ex = task.getException();
            ex.printStackTrace();
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error fetching clients history");
                alert.setHeaderText(ex.getClass().getSimpleName());
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
              //  ExpiredToken.RedirectAfterExpire();
            }); */
            APIErrorHandler.handle(task.getException());
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }

    private void ClientProfile(Client client){
//       TableView<ClientHistory> historyTable = new TableView<>();

  //     historyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
       
    TableColumn<ClientHistory, String> eshCol = new TableColumn<>("Sherbimi");
    eshCol.setCellValueFactory(new PropertyValueFactory<>("emri_sherbimit"));

        TableColumn<ClientHistory, String> pershkrimiCol = new TableColumn<>("Pershkrimi");
    pershkrimiCol.setCellValueFactory(new PropertyValueFactory<>("pershkrimi"));

    TableColumn<ClientHistory, String> atrCol = new TableColumn<>("Atributi i sherbimit");
    atrCol.setCellValueFactory(new PropertyValueFactory<>("emri_atributit"));

    TableColumn<ClientHistory, Double> qmimiBazik = new TableColumn<>("Qmimi fillestar");
    qmimiBazik.setCellValueFactory(new PropertyValueFactory<>("qmimiBazik"));

    TableColumn<ClientHistory, Double> qmimiFinal = new TableColumn<>("Qmimi final");
    qmimiFinal.setCellValueFactory(new PropertyValueFactory<>("pagesa"));

    TableColumn<ClientHistory, String> kohezgjatjaCol = new TableColumn<>("Kohëzgjatja");

      TableColumn<ClientHistory, Integer> zbrCol = new TableColumn<>("Zbritja");
    zbrCol.setCellValueFactory(new PropertyValueFactory<>("zbritja"));

kohezgjatjaCol.setCellValueFactory(cd ->
    new SimpleStringProperty(
        cd.getValue().getKohezgjatja()
            .format(DateTimeFormatter.ofPattern("HH:mm"))
    )
);

    TableColumn<ClientHistory, String> dateCol = new TableColumn<>("Data e sherbimit");
    dateCol.setCellValueFactory(cd ->
        new SimpleStringProperty(
            cd.getValue().getData_sherbimit()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        )
    );

    historyTable.getColumns().setAll(List.of(dateCol,eshCol, atrCol, pershkrimiCol, kohezgjatjaCol, qmimiBazik, zbrCol, qmimiFinal));

    historyTable.getItems().clear();

    if(client.getClientHistory() == null){
        fetchClientHistory(client);
    } else{
    historyTable.setItems(FXCollections.observableArrayList(client.getClientHistory()));
    }
 
    Label title = new Label(
        "History for " + client.getEmri() + " " + client.getMbiemri()
    );

    VBox root = new VBox(10, title, historyTable);
    root.setPadding(new Insets(10));

    Stage stage = new Stage();
    stage.setTitle("Client History");
    stage.setScene(new Scene(root, 600, 400));
    stage.showAndWait();
    }
}

