package com.beautysalon.Controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.API.ClientsAPI;
import com.beautysalon.gate.Configuration.ExecutorConfig;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;
import com.beautysalon.gate.Model.clients.Client;
import com.beautysalon.gate.Model.clients.ClientHistory;
import com.beautysalon.gate.Model.clients.Historiku_detajet;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientProfileController {
    
    @FXML
    private TextField emriField;

    @FXML
    private TableView<ClientHistory> historyField;

    private TableView<Historiku_detajet> historikuDetajetTable = new TableView<>();

    @FXML
    private TextField mbiemriField;

    @FXML
    private TextField numriTelField;

     @FXML
    private TableColumn<ClientHistory, Long> idCol;

    @FXML
    private TableColumn<ClientHistory, String> emriPunonjesitCol;

    @FXML
    private TableColumn<ClientHistory, String> dataSherbimitCol;

    @FXML
    private Button rollbackBtn;

    private ClientsAPI clientsService = new ClientsAPI();

    private TableColumn<Historiku_detajet, String> sherbimiCol = new TableColumn<>("Sherbimi");
private TableColumn<Historiku_detajet, String> atributiCol = new TableColumn<>("Atributi");
private TableColumn<Historiku_detajet, String> pershkrimiCol = new TableColumn<>("Pershkrimi");
private TableColumn<Historiku_detajet, Double> pagesaCol = new TableColumn<>("Pagesa");

    @FXML
    public void initialize(){

        Client client = SessionManager.getClient();

         if (client == null) {
        System.err.println("No client in session!");
        return;
    }

  historikuDetajetTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        emriField.setText(client.getEmri());
        mbiemriField.setText(client.getMbiemri());
        numriTelField.setText(client.getNumri_telefonit());

        idCol.setCellValueFactory(new PropertyValueFactory<>("id_historikut"));
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

       historyField.setRowFactory(tv -> {
    TableRow<ClientHistory> row = new TableRow<>();

    row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && !row.isEmpty()) { // double click (recommended)
            
            ClientHistory selected = row.getItem();

            List<Historiku_detajet> details =
                    selected.getDetajet(); // adjust if your getter name differs

            HistorikuDetajet(details); // open popup
        }
    });

    return row;
});
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

  private void HistorikuDetajet(List<Historiku_detajet> data){

    // columns
     TableColumn<Historiku_detajet, Long> idCol = new TableColumn<>("ID");
    TableColumn<Historiku_detajet, String> sherbimiCol = new TableColumn<>("Sherbimi");
    TableColumn<Historiku_detajet, String> atributiCol = new TableColumn<>("Atributi");
    TableColumn<Historiku_detajet, String> pershkrimiCol = new TableColumn<>("Pershkrimi");
    TableColumn<Historiku_detajet, Double> pagesaCol = new TableColumn<>("Pagesa");

    // bindings
    idCol.setCellValueFactory(new PropertyValueFactory<>("id_historikut_detajet"));
    sherbimiCol.setCellValueFactory(new PropertyValueFactory<>("emri_sherbimit"));
    atributiCol.setCellValueFactory(new PropertyValueFactory<>("emri_atributit"));
    pershkrimiCol.setCellValueFactory(new PropertyValueFactory<>("pershkrimi"));
    pagesaCol.setCellValueFactory(new PropertyValueFactory<>("pagesa"));

    TableView<Historiku_detajet> table = new TableView<>();
    table.getColumns().setAll(List.of(idCol, sherbimiCol, atributiCol, pershkrimiCol, pagesaCol));

    table.setItems(FXCollections.observableArrayList(data));

    VBox root = new VBox(10, table);
    root.setPadding(new Insets(10));

    Stage stage = new Stage();
    stage.setTitle("Detajet e Historise");
    stage.setScene(new Scene(root, 600, 400));
     stage.showAndWait();
}
}
