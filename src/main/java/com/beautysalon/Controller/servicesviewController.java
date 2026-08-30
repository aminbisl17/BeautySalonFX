package com.beautysalon.Controller;

import java.util.List;
import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.API.ServiceCall;
import com.beautysalon.gate.Configuration.ModernAlert;
import com.beautysalon.gate.Model.services.Sherbimet;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
public class servicesviewController {

    @FXML
    private Button refreshbtn;

    @FXML
    private Button registerbtn;

    @FXML
    private TableView<Sherbimet> table;

      @FXML
    private TextField searchField;

    boolean serverNotification;

    @FXML
    private void initialize(){
         
        if(SessionManager.getPrimaryResponse().getRole().equals("ROLE_ADMIN")) registerbtn.setVisible(true);

        registerbtn.setOnAction((_)->{
            CenterController.loadCenterContent("ServiceRegisterForm.fxml");
        });

         table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

         table.setPlaceholder(new ProgressIndicator());

         refreshbtn.setOnAction((_)->{     table.setItems(FXCollections.observableArrayList()); fetchServices(); });

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
                         // atributetSherbimeve(row.getItem());
                         SessionManager.setSherbimi(row.getItem());
                         CenterController.loadCenterContent("servicedetails.fxml");
                   }
            });
            return row;
         });

         if(SessionManager.getSherbimet() == null){
            fetchServices();
            return;
         }

         setupSearch(SessionManager.getSherbimet());
    }

    private void fetchServices(){
        ServiceCall.fetchServices().thenAccept(e ->{
                if (e && SessionManager.getSherbimet() != null) {
               serverNotification = true;
                Platform.runLater(() ->
                        setupSearch(SessionManager.getSherbimet()));
            } 
        })
        .exceptionally(ex -> {
    
         serverNotification = false;
                    Platform.runLater(() -> {
        ModernAlert.warning(
                "Server unreachable",
                "Failed to fetch service data!"
        );
        });

        return null;
    });
}
    
     private void setupSearch(List<Sherbimet> sherbimet) {

        if (sherbimet == null) return;

        FilteredList<Sherbimet> filteredData =
                new FilteredList<>(
                        FXCollections.observableArrayList(sherbimet),
                        b -> true
                );

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {

            filteredData.setPredicate(sherbimi -> {

                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }

                String keyword = newVal.toLowerCase();

                return (sherbimi.getEmri_sherbimit() != null &&
                        sherbimi.getEmri_sherbimit().toLowerCase().contains(keyword));

                               });
        });

        SortedList<Sherbimet> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
       if(!sortedData.isEmpty()){
        table.setItems(sortedData);
        return;
       }
          table.setPlaceholder(new Label("Nuk ka shërbim të regjistruar!"));
        table.setItems(FXCollections.observableArrayList());
    }

}
