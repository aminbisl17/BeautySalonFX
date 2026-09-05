package com.beautysalon.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import org.glassfish.grizzly.http.server.Session;

import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.API.ClientCall;
import com.beautysalon.gate.API.ServiceCall;
import com.beautysalon.gate.Configuration.ModernAlert;
import com.beautysalon.gate.DTO.SherbimetUpdateDTO;
import com.beautysalon.gate.Model.clients.Client;
import com.beautysalon.gate.Model.services.Atributet_sherbimeve;
import com.beautysalon.gate.Model.services.Sherbimet;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.util.StringConverter;

public class servicedetails {
    
     
      @FXML
    private TextField cmimiField;

    @FXML
    private TableColumn<Sherbimet, String> emriCol;


    @FXML
    private TextField EmriField;

    @FXML
    private TableView<Atributet_sherbimeve> atributetTable;

    @FXML
    private TableColumn<Atributet_sherbimeve, Integer> idCol;

    @FXML
    private Label idLabel;

    @FXML
    private TableColumn<Atributet_sherbimeve, String> kohezgjatjaCol;

    @FXML
    private TableColumn<Atributet_sherbimeve, String> pershkrimiCol;

    @FXML
    private TextArea pershkrimiField;

    @FXML
    private Button rollbackBtn;
    
        @FXML
    private Button deleteBtn;

    @FXML
    private Button editBtn;

        @FXML
    private Button cancelBtn;

       @FXML
    private Button saveBtn;


    @FXML
    private TableColumn<Atributet_sherbimeve, Integer> zbritjaCol;

       @FXML
    private TextField zbritjaField;

    @FXML
    private Spinner<LocalTime> kohezgjatjaField;

    private Sherbimet sherbimi = SessionManager.getSherbimi();

    @FXML
    ImageView serviceImage;

       @FXML
    private TextField searchField;
    
    @FXML
    public void initialize(){
          
        if(sherbimi == null) ModernAlert.warning("Deshtim", "Nuk ka te dhena!");

        if(SessionManager.getPrimaryResponse().getRole().equals("ROLE_ADMIN")){
            editBtn.setVisible(true);
            deleteBtn.setVisible(true);
        }

        setEditMode(false);
        
        serviceImage.setClip(new Circle(45, 45, 45));

         idLabel.setText(sherbimi.getID().toString());
         EmriField.setText(sherbimi.getEmri_sherbimit());
       pershkrimiField.setText(sherbimi.getPershkrimi());

       SpinnerValueFactory<LocalTime> valueFactory =
        new SpinnerValueFactory<LocalTime>() {

            {
                setConverter(new StringConverter<LocalTime>() {
                    @Override
                    public String toString(LocalTime time) {
                        return time == null ? "" : time.format(
                            DateTimeFormatter.ofPattern("HH:mm:ss")
                        );
                    }

                    @Override
                    public LocalTime fromString(String string) {
                        return LocalTime.parse(
                            string,
                            DateTimeFormatter.ofPattern("HH:mm:ss")
                        );
                    }
                });

                setValue(LocalTime.of(0, 30, 0)); // initial value
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(steps));
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(steps));
            }
        };

kohezgjatjaField.setValueFactory(valueFactory);

       
        editBtn.setOnAction(e -> setEditMode(true));

        saveBtn.setOnAction(e -> saveChanges());

        deleteBtn.setOnAction(e -> 

              ModernAlert.confirm("Fshij Shërbimin", "Dëshironi të fshijni këtë shërbim?")
        .ifPresent(response -> {

            ServiceCall.deleteService(sherbimi.getID()).thenAccept( success ->{

                if(success){
                    Platform.runLater(()->{

                ServiceCall.fetchServices();
                ModernAlert.success("Informatë", "Shërbimi u fshi me sukses!");
                 CenterController.loadCenterContent("servicesview.fxml");
            });
                }
    
            }).exceptionally(ex->{
                     System.out.println(ex);
                  ModernAlert.warning(
                "Server unreachable",
                "Failed to delete service !"
        );
                return null;
            });
        })

        );

        cancelBtn.setOnAction(e -> cancelEdit());

         rollbackBtn.setOnAction( (_) -> {CenterController.loadCenterContent("servicesview.fxml");});


 
         fetchServiceAtributes();

    }

    private void fetchServiceAtributes(){
int totalSeconds = sherbimi.getKohezgjatja();

LocalTime duration = LocalTime.ofSecondOfDay(totalSeconds);

kohezgjatjaField.getValueFactory().setValue(duration);
         zbritjaField.setText(String.valueOf(sherbimi.getZbritja()));
         cmimiField.setText(String.valueOf(sherbimi.getQmimi_baze()));
         
ServiceCall.fetchServiceAtributes(sherbimi.getID()).thenAccept(e -> {
    if (e != null) {
        Platform.runLater(() -> {
            idCol.setCellValueFactory(new PropertyValueFactory<>("id_atributit"));
            emriCol.setCellValueFactory(new PropertyValueFactory<>("opsioni"));
            pershkrimiCol.setCellValueFactory(new PropertyValueFactory<>("pershkrimi"));

            kohezgjatjaCol.setCellValueFactory(cellData -> {
                int secs = cellData.getValue().getKohezgjatja();

                int hour = secs / 3600;
                int minute = (secs % 3600) / 60;

              String formated = (hour > 0)
        ? hour + "h " + minute + "m"
        : minute + "m";

                return new SimpleStringProperty(formated);
            });

            //atributetTable.setItems(FXCollections.observableArrayList(e.getAtributet()));
            setupSearch(e.getAtributet());

                    if (e.getImagePath() != null && !e.getImagePath().isBlank()) {
            byte[] imageBytes = Base64.getDecoder().decode(e.getImagePath());
            InputStream is = new ByteArrayInputStream(imageBytes);
            Image image = new Image(is);
            serviceImage.setImage(image);
            return;
        }

        });
    }
}).exceptionally(e ->{
       ModernAlert.warning(
                "Server unreachable",
                "Failed to fetch service data!"
        );
    return null;
});

    }

    private void updateService(Sherbimet s){

         SherbimetUpdateDTO dto = new SherbimetUpdateDTO();

    dto.setEmri_sherbimit(s.getEmri_sherbimit());
    dto.setPershkrimi(s.getPershkrimi());
    dto.setQmimi_baze(s.getQmimi_baze());
    dto.setZbritja(s.getZbritja());
    dto.setKohezgjatja(s.getKohezgjatja());
    dto.setAtributet(s.getAtributet());

    // Set these according to your UI/state
    dto.setIs_active(true);
    dto.setRemoveImage(false);

    ServiceCall.updateServices(dto, s.getID()).thenAccept(
        success ->{
            Platform.runLater(()->{
                ModernAlert.success("Success", "Service updated");
            });
        }
    ).exceptionally(ex ->{

        Platform.runLater(()->{
            ModernAlert.danger("Failed", ex.getMessage());
        });
        return null;
    });

}


      private void setupSearch(List<Atributet_sherbimeve> sherbimet) {

        if (sherbimet == null) return;

        FilteredList<Atributet_sherbimeve> filteredData =
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

                return (sherbimi.getOpsioni() != null &&
                        sherbimi.getOpsioni().toLowerCase().contains(keyword));

                               });
        });

        SortedList<Atributet_sherbimeve> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(atributetTable.comparatorProperty());
       if(!sortedData.isEmpty()){
        atributetTable.setItems(sortedData);
        return;
       }
        atributetTable.setPlaceholder(new Label("Nuk ka shërbim të regjistruar!"));
        atributetTable.setItems(FXCollections.observableArrayList());
    }

    private void setEditMode(Boolean enabled){
      
        EmriField.setEditable(enabled);
        cmimiField.setEditable(enabled);
        pershkrimiField.setEditable(enabled);
        kohezgjatjaField.setEditable(enabled);
        zbritjaField.setEditable(enabled);

        editBtn.setVisible(!enabled);
        editBtn.setManaged(!enabled);
     
        saveBtn.setVisible(enabled);
        saveBtn.setManaged(enabled);

        cancelBtn.setVisible(enabled);
        cancelBtn.setManaged(enabled);

             addEditModeStyle(EmriField, enabled);
addEditModeStyle(cmimiField, enabled);
addEditModeStyle(pershkrimiField, enabled);
addEditModeStyle(kohezgjatjaField, enabled);
addEditModeStyle(zbritjaField, enabled);
    }

     private void cancelEdit() {

        ModernAlert.warning("Kujdes", "Dëshironi të anuloni ndryshimet?").ifPresent(response -> {

            if (response == ButtonType.OK) {

        EmriField.setText(sherbimi.getEmri_sherbimit());
        cmimiField.setText(String.valueOf(sherbimi.getQmimi_baze()));
        zbritjaField.setText(String.valueOf(sherbimi.getZbritja()));
        pershkrimiField.setText(sherbimi.getPershkrimi());

        int totalSeconds = sherbimi.getKohezgjatja();

kohezgjatjaField.getValueFactory()
        .setValue(LocalTime.ofSecondOfDay(totalSeconds));
        
         zbritjaField.setText(String.valueOf(sherbimi.getZbritja()));
         cmimiField.setText(String.valueOf(sherbimi.getQmimi_baze()));
         
        setEditMode(false);

        //        CenterController.loadCenterContent("clientsview.fxml");
            }
        });

      
    }

    private void saveChanges() {

    ModernAlert.confirm("Ruaj ndryshimet", "Dëshironi të bëni ndryshime?")
        .ifPresent(response -> {

            if (response == ButtonType.OK) {
               // updateClient();

                sherbimi.setEmri_sherbimit(EmriField.getText());
                sherbimi.setQmimi_baze(Double.parseDouble(cmimiField.getText()));
                sherbimi.setPershkrimi(pershkrimiField.getText());
                sherbimi.setZbritja(Integer.parseInt(zbritjaField.getText()));
            
                LocalTime duration = kohezgjatjaField.getValue();

int totalSeconds = duration.toSecondOfDay();

sherbimi.setKohezgjatja(totalSeconds);

               updateService(sherbimi);
                setEditMode(false);
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


}
