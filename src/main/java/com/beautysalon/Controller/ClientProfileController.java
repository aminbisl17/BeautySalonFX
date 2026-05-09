package com.beautysalon.Controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.API.ClientsAPI;
import com.beautysalon.gate.Configuration.ExecutorConfig;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;
import com.beautysalon.gate.Model.clients.Client;
import com.beautysalon.gate.Model.clients.ClientHistory;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientProfileController {
    
    @FXML
    private TextField emriField;

    @FXML
    private TableView<ClientHistory> historyField;

    @FXML
    private TextField mbiemriField;

    @FXML
    private TextField numriTelField;

    @FXML
    private TableColumn<ClientHistory, String> emriPunonjesitCol;

    @FXML
    private TableColumn<ClientHistory, String> dataSherbimitCol;

    private ClientsAPI clientsService = new ClientsAPI();

    @FXML
    public void initialize(){

        Client client = SessionManager.getClient();

         if (client == null) {
        System.err.println("No client in session!");
        return;
    }


        emriField.setText(client.getEmri());
        mbiemriField.setText(client.getMbiemri());
        numriTelField.setText(client.getNumri_telefonit());

        emriPunonjesitCol.setCellValueFactory(new PropertyValueFactory<>("emri_mbiemri_punonjesit"));

        dataSherbimitCol.setCellValueFactory(cd ->
        new SimpleStringProperty(
            cd.getValue().getData_sherbimit()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        )
    );
    
     // List<ClientHistory> history = client.getClientHistory(); // adjust if needed
   // if (history == null) {
       // historyField.getItems().setAll(history);
       fetchClientHistory(client);
    //}
    
    //historyField.getColumns().setAll(List.of(emriField, dataSherbimitCol));

    }

     private void fetchClientHistory(Client client){
      Task<List<ClientHistory>> task = new Task<>(){

        @Override
        protected List<ClientHistory> call() throws Exception {
           return clientsService.getClientHistory(client.getID());
        }
      };

         task.setOnSucceeded((_)->{
            
                //List<ClientHistory> history = task.getValue();
             //   client.setClientHistory(task.getValue());
                historyField.setItems(FXCollections.observableArrayList(task.getValue()));
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

       ExecutorConfig.submit(task);

    }

}
