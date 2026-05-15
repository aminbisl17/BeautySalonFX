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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
    private TextField emailField;

    @FXML private Label idLabel;
@FXML private Label gjiniaLabel;
@FXML private Label usernameLabel;
@FXML private Label dataRegjistrimitLabel;

    @FXML
    private TableColumn<ClientHistory, Long> idCol;

    @FXML
    private TableColumn<ClientHistory, String> emriPunonjesitCol;

    @FXML
    private TableColumn<ClientHistory, String> dataSherbimitCol;

    @FXML
    private Button rollbackBtn;

    @FXML
    private Button editBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button cancelBtn;

    @FXML
    private TextArea pershkrimiField;

    private boolean editMode = false;

    private ClientsAPI clientsService = new ClientsAPI();

//    private TableColumn<Historiku_detajet, String> sherbimiCol = new TableColumn<>("Sherbimi");
  //  private TableColumn<Historiku_detajet, String> atributiCol = new TableColumn<>("Atributi");
   // private TableColumn<Historiku_detajet, String> pershkrimiCol = new TableColumn<>("Pershkrimi");
   // private TableColumn<Historiku_detajet, Double> pagesaCol = new TableColumn<>("Pagesa");

    @FXML
    public void initialize() {

        Client client = SessionManager.getClient();

        if (client == null) {
            System.err.println("No client in session!");
            return;
        }
 
        historyField.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        historikuDetajetTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        emriField.setText(client.getEmri());
        mbiemriField.setText(client.getMbiemri());
        numriTelField.setText(client.getNumri_telefonit());

        emailField.setText(client.getEmail());

        

        pershkrimiField.setText(client.getPershkrimi());

       idLabel.setText(String.valueOf(client.getID()));
gjiniaLabel.setText(client.getGjinia());
usernameLabel.setText(client.getUsername());

if (client.getData_regjistrimit() != null) {
    dataRegjistrimitLabel.setText(
        client.getData_regjistrimit()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    );
}
        idCol.setCellValueFactory(new PropertyValueFactory<>("id_historikut"));
        emriPunonjesitCol.setCellValueFactory(new PropertyValueFactory<>("emri_mbiemri_punonjesit"));

        dataSherbimitCol.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue().getData_sherbimit()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        setEditMode(false);

        editBtn.setOnAction(e -> setEditMode(true));

        saveBtn.setOnAction(e -> saveChanges());

        deleteBtn.setOnAction(e -> deleteClient());

        cancelBtn.setOnAction(e -> cancelEdit());

        fetchClientHistory(client);

        historyField.setRowFactory(tv -> {
            TableRow<ClientHistory> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) { // double click (recommended)

                    ClientHistory selected = row.getItem();

                    List<Historiku_detajet> details = selected.getDetajet(); // adjust if your getter name differs

                    HistorikuDetajet(details); // open popup
                }
            });

            return row;
        });
        // }

        // historyField.getColumns().setAll(List.of(emriField, dataSherbimitCol));

    }

    private void setEditMode(boolean enable) {

        editMode = enable;

        // editable only in edit mode
        emriField.setEditable(enable);
        mbiemriField.setEditable(enable);
        numriTelField.setEditable(enable);
        emailField.setEditable(enable);
        pershkrimiField.setEditable(enable);

       emriField.setFocusTraversable(enable);
        mbiemriField.setFocusTraversable(enable);
        numriTelField.setFocusTraversable(enable);
        emailField.setFocusTraversable(enable);
        pershkrimiField.setFocusTraversable(enable);

        editBtn.setVisible(!enable);
        editBtn.setManaged(!enable);

        saveBtn.setVisible(enable);
        saveBtn.setManaged(enable);

        cancelBtn.setVisible(enable);
        cancelBtn.setManaged(enable);

     addEditModeStyle(emriField, enable);
addEditModeStyle(mbiemriField, enable);
addEditModeStyle(numriTelField, enable);
addEditModeStyle(emailField, enable);
addEditModeStyle(pershkrimiField, enable);
    }

    private void saveChanges() {    
        
        Client client = SessionManager.getClient();
       //Client updated = new Client();

        client.setEmri(emriField.getText());
        client.setMbiemri(mbiemriField.getText());
        client.setNumri_telefonit(numriTelField.getText());
        client.setEmail(emailField.getText());
        client.setPershkrimi(pershkrimiField.getText());

        
        updateClient(client);
        setEditMode(false);
    }

    private void cancelEdit() {

        Client client = SessionManager.getClient();

        emriField.setText(client.getEmri());
        mbiemriField.setText(client.getMbiemri());
        numriTelField.setText(client.getNumri_telefonit());
        emailField.setText(client.getEmail());
        pershkrimiField.setText(client.getPershkrimi());
        setEditMode(false);
    }

    private void deleteClient() {

        Client client = SessionManager.getClient();

        // optional confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Client");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("This action cannot be undone.");

        alert.showAndWait().ifPresent(response -> {

            if (response == ButtonType.OK) {

                // clientsService.deleteClient(client.getID());

                CenterController.loadCenterContent("clientsview.fxml");
            }
        });
    }

private void addEditModeStyle(javafx.scene.Node node, boolean enable) {
    if (enable) {
        if (!node.getStyleClass().contains("edit-mode")) {
            node.getStyleClass().add("edit-mode");
        }
    } else {
        node.getStyleClass().remove("edit-mode");
    }
}

    private void fetchClientHistory(Client client) {

        Task<List<ClientHistory>> task = new Task<>() {

            @Override
            protected List<ClientHistory> call() throws Exception {
                return clientsService.getClientHistory(client.getID());
            }
        };

        task.setOnSucceeded((_) -> {

            // List<ClientHistory> history = task.getValue();
            // client.setClientHistory(task.getValue());
            historyField.setItems(FXCollections.observableArrayList(task.getValue()));
        });

        task.setOnFailed((_) -> {
            /*
             * Throwable ex = task.getException();
             * ex.printStackTrace();
             * Platform.runLater(() -> {
             * Alert alert = new Alert(Alert.AlertType.ERROR);
             * alert.setTitle("Error fetching clients history");
             * alert.setHeaderText(ex.getClass().getSimpleName());
             * alert.setContentText(ex.getMessage());
             * alert.showAndWait();
             * // ExpiredToken.RedirectAfterExpire();
             * });
             */
            APIErrorHandler.handle(task.getException());
        });

        ExecutorConfig.submit(task);

    }

    private void updateClient(Client client){
 
        Task<String> task = new Task<>(){

            @Override
            protected String call() throws Exception {
                return clientsService.updateClient(client);
            }
      
        };

        task.setOnFailed((_)->{
            APIErrorHandler.handle(task.getException());
        });
        task.setOnSucceeded((_)->{
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText(task.getValue());
                alert.showAndWait();
        });
 ExecutorConfig.submit(task);
    }

    private void HistorikuDetajet(List<Historiku_detajet> data) {

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
        table.getStyleClass().add("popup-table");
        table.getColumns().setAll(List.of(idCol, sherbimiCol, atributiCol, pershkrimiCol, pagesaCol));

        table.setItems(FXCollections.observableArrayList(data));

        VBox root = new VBox(15, table);
        root.getStyleClass().add("popup-root");
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 600, 400);

        scene.getStylesheets().add(
                getClass().getResource("/css/ClientProfile.css").toExternalForm());

        Stage stage = new Stage();
        stage.setTitle("Detajet e Historise");

        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        stage.initOwner(historyField.getScene().getWindow());

        stage.setScene(scene);
        stage.setResizable(false);

        stage.showAndWait();
    }
}
