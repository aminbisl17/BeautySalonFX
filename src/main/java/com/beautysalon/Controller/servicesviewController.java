package com.beautysalon.Controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.beautysalon.gate.API.SessionManager;
import com.beautysalon.gate.API.Services.ServicesService;
import com.beautysalon.gate.Model.services.Atributet_sherbimeve;
import com.beautysalon.gate.Model.services.Sherbimet;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class servicesviewController {

    private ServicesService service = new ServicesService();

    @FXML
    private Button refreshbtn;

    @FXML
    private TableView<Sherbimet> table;

    @FXML
    private void initialize(){
         table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

         refreshbtn.setOnAction((_)->{ table.getItems().clear(); fetchServices(); });

         TableColumn<Sherbimet, Integer> idcol = new TableColumn<>("ID");
         idcol.setCellValueFactory(new PropertyValueFactory<>("ID"));

         TableColumn<Sherbimet, String> emrcol = new TableColumn<>("Emri");
         emrcol.setCellValueFactory(new PropertyValueFactory<>("emri_sherbimit"));

         TableColumn<Sherbimet, String> prcol = new TableColumn<>("Pershkrimi");
         prcol.setCellValueFactory(new PropertyValueFactory<>("pershkrimi"));

         TableColumn<Sherbimet, Double> qfcol = new TableColumn<>("Qmimi fillestar");
         qfcol.setCellValueFactory(new PropertyValueFactory<>("qmimi_baze"));

         TableColumn<Sherbimet, Integer> zbrcol = new TableColumn<>("Zbritja");
         zbrcol.setCellValueFactory(new PropertyValueFactory<>("ID"));
         
         table.getColumns().setAll(List.of(idcol, emrcol, prcol, qfcol, zbrcol));

         
         table.setRowFactory((_) -> {
            TableRow<Sherbimet> row = new TableRow<>();

            row.setOnMouseClicked((event)->{
                   if(!row.isEmpty() && event.getClickCount() == 2){
                          atributetSherbimeve(row.getItem());
                   }
            });
            return row;
         });

         if(SessionManager.getSherbimet() == null){
            fetchServices();
            return;
         }

         table.setItems(FXCollections.observableList(SessionManager.getSherbimet()));
    }

    private void fetchServices(){

        Task<List<Sherbimet>> task = new Task<>(){

            @Override
            protected List<Sherbimet> call() throws Exception {
                return service.getAllSherbimet();
            }  
        };

        task.setOnSucceeded((_)->{
            SessionManager.setSherbimet(task.getValue());
            table.setItems(FXCollections.observableList(task.getValue()));
        });

        task.setOnFailed((_)->{
            Throwable e = task.getException();
              e.printStackTrace();
              Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error fetching Services");
                alert.setHeaderText(e.getClass().getSimpleName());
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            });
        }); 

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }

    private void atributetSherbimeve(Sherbimet s){

        TableView<Atributet_sherbimeve> t = new TableView<>();

        t.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        TableColumn<Atributet_sherbimeve, Integer> idcol = new TableColumn<>("ID");
         idcol.setCellValueFactory(new PropertyValueFactory<>("id_atributit"));

         TableColumn<Atributet_sherbimeve, String> kzcol = new TableColumn<>("Kohëzgjatja");
         kzcol.setCellValueFactory(cd ->
    new SimpleStringProperty(
        cd.getValue().getKohezgjatja()
            .format(DateTimeFormatter.ofPattern("HH:mm"))
    ));


         TableColumn<Atributet_sherbimeve, String> opcol = new TableColumn<>("Emri");
         opcol.setCellValueFactory(new PropertyValueFactory<>("opsioni"));

         TableColumn<Atributet_sherbimeve, String> pscol = new TableColumn<>("Pershkrimi");
         pscol.setCellValueFactory(new PropertyValueFactory<>("pershkrimi"));

         TableColumn<Atributet_sherbimeve, Double> qcol = new TableColumn<>("Qmimi");
         qcol.setCellValueFactory(new PropertyValueFactory<>("qmimi"));


         TableColumn<Atributet_sherbimeve, Integer> zcol = new TableColumn<>("Zbritja");
         zcol.setCellValueFactory(new PropertyValueFactory<>("zbritja"));

         t.getColumns().setAll(List.of(idcol, opcol, pscol, kzcol, zcol, qcol));

         t.getItems().addAll(s.getAtributet());

         Label title = new Label(
        "Atributet e sherbimeve " + s.getEmri_sherbimit()
    );

    VBox root = new VBox(10, title, t);
    root.setPadding(new Insets(10));

    Stage stage = new Stage();
    stage.setTitle("Client History");
    stage.setScene(new Scene(root, 600, 400));
    stage.showAndWait();
    }

}
