package com.beautysalon.Controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.beautysalon.gate.API.Services.ServicesService;
import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;
import com.beautysalon.gate.Model.services.Atributet_sherbimeve;
import com.beautysalon.gate.Model.services.Sherbimet;
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

public class servicesviewController {

    private ServicesService service = new ServicesService();

    @FXML
    private Button refreshbtn;

    @FXML
    private TableView<Sherbimet> table;

    private TableView<Atributet_sherbimeve> t = new TableView<>();

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

        Task<Void> task = new Task<>(){

            @Override
            protected Void call() throws Exception {
                service.getAllSherbimet();
                return null;
            }  
        };

        task.setOnSucceeded((_)->{
            
            table.setItems(FXCollections.observableList(SessionManager.getSherbimet()));
        });

        task.setOnFailed((_)->{
        /*     Throwable e = task.getException();
              e.printStackTrace();
              Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error fetching Services");
                alert.setHeaderText(e.getClass().getSimpleName());
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                ExpiredToken.RedirectAfterExpire(); //(Stage) refreshbtn.getScene().getWindow()
            }); */

            APIErrorHandler.handle(task.getException());
        }); 

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }

    private void fetchAtributetSherbimeve(Long ID){
        Task<List<Atributet_sherbimeve>> task = new Task<>(){

            @Override
            protected List<Atributet_sherbimeve> call() throws Exception {
                return service.getAtributet_sherbimit(ID);
            }

        };

        task.setOnFailed((_) ->{
           APIErrorHandler.handle(task.getException());
        });

        task.setOnSucceeded((_)->{
           t.setItems(FXCollections.observableArrayList(task.getValue()));
        });

        Thread th = new Thread(task);
         th.setDaemon(true);
        th.start();
    }

    private void atributetSherbimeve(Sherbimet s){

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

      //   t.getItems().addAll(FXCollections.observableList(s.getAtributet()));
        t.getItems().clear();

        fetchAtributetSherbimeve(s.getID());

         Label title = new Label(
        "Atributet e sherbimeve " + s.getEmri_sherbimit()
    );

    VBox root = new VBox(10, title, t);
    root.setPadding(new Insets(10));

    Stage stage = new Stage();
    stage.setTitle("Kategorite");
    stage.setScene(new Scene(root, 600, 400));
    stage.showAndWait();
    }

}
