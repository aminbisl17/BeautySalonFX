package com.beautysalon.Backend.Controllers;

import java.io.IOException;
import java.util.List;

import com.beautysalon.Client;
import com.beautysalon.ClientService;
import com.beautysalon.Backend.Controllers.klientet.dialogues.clientProfileFormController;
import com.beautysalon.Backend.Controllers.klientet.dialogues.registerFormController;
import com.beautysalon.Backend.Database.Database;
import com.beautysalon.Backend.Database.Entities.clientsData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EmployeeDashboardController {

    @FXML
    private BorderPane appointmentsView;

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
    private TableView<Client> table;

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
    private Button regbtn;

    private List<Pane> cards;

    private void setupRowClickListener() {
    table.setRowFactory((_) -> {
        TableRow<Client> row = new TableRow<>();
        row.setOnMouseClicked((_) -> {
            if (!row.isEmpty()) { // double-click
                Client selectedClient = row.getItem();
            //   showClientDialog(selectedClient);
            }
        });
        return row;
    });
}

        void showClientDialog(clientsData client) {

            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/klientet/dialogues/clientProfileForm.fxml"));
            Parent root;
            try {
                root = fxmlLoader.load();

            ((clientProfileFormController)fxmlLoader.getController()).setData(client.getEmri(), client.getMbiemri(), client.getNumri_telefonit(), client.getGjinia(), client.getPershkrimi(), client.getData_regjistrimit());;

            Stage stage = new Stage();
            stage.setTitle("Register Client");
            stage.setScene(new Scene(root));

        
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            } catch (IOException e) {
            
                e.printStackTrace();
            }
   }

    public void showPage(int targetIndex){
       for (int i = 0; i < cards.size(); i++) {
        Pane card = cards.get(i);
        boolean visible = (i == targetIndex);

        card.setVisible(visible);          // show/hide visually
        card.setManaged(visible);          // include/exclude from layout
        card.setDisable(!visible);         // prevent hidden pane from handling input
        card.setMouseTransparent(!visible); // allow clicks to pass through hidden panes
    }
    }

    public void loadData() {


        table.getItems().clear();

        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colEmri.setCellValueFactory(new PropertyValueFactory<>("emri"));
        colMbiemri.setCellValueFactory(new PropertyValueFactory<>("mbiemri"));
        colNumri.setCellValueFactory(new PropertyValueFactory<>("numri_telefonit"));
        colPershkrimi.setCellValueFactory(new PropertyValueFactory<>("pershkrimi"));
        colGjinia.setCellValueFactory(new PropertyValueFactory<>("gjinia"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data_regjistrimit"));

     //   List<clientsData> list = Database.getAllClients();

      ClientService service = new ClientService();

// Fetch clients from API
List<Client> clientList;
try {
    clientList = service.getClients();


// Wrap into ObservableList for JavaFX TableView
ObservableList<Client> clients = FXCollections.observableArrayList(clientList);

// Set items to TableView

    table.setItems(clients);
    // Optional: print all clients to console
    clients.forEach(System.out::println);
} catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}
    }
    @FXML
    public void initialize() {
    pageTitle.setText("Klientet");

    loadData();
    setupRowClickListener();

    try {
        BorderPane appointments = new FXMLLoader(getClass().getResource("/fxml/terminet/appointments.fxml")).load();

        spED.getChildren().addAll(appointments);

        cards = List.of(clientTable, appointments);

        for (Pane card : cards) {
            card.prefWidthProperty().bind(spED.widthProperty());
            card.prefHeightProperty().bind(spED.heightProperty());
        }

    } catch (IOException e) {
        e.printStackTrace();
    }

    showPage(0);
    }

    @FXML
    void close(ActionEvent event) {
        ((Stage) closebtn.getScene().getWindow()).close();
    }

    @FXML
    void registerClient() {
        try {
    
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/klientet/dialogues/registerClientForm.fxml"));
            Parent root = fxmlLoader.load();

            ((registerFormController)fxmlLoader.getController()).setEc(this);

         
            Stage stage = new Stage();
            stage.setTitle("Register Client");
            stage.setScene(new Scene(root));

        
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
private void onButton1Clicked() { showPage(0); }

@FXML
private void onButton2Clicked() { showPage(1); }

@FXML
private void onButton3Clicked() { /* showCard(2);  */ }

}
