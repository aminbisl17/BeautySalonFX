package com.beautysalon.Controller;

import java.util.List;

import com.beautysalon.gate.API.SessionManager;
import com.beautysalon.gate.API.Services.ServicesService;
import com.beautysalon.gate.Model.services.Sherbimet;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

}
