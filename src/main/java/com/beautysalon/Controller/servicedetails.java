package com.beautysalon.Controller;

import java.util.List;

import org.glassfish.grizzly.http.server.Session;

import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.Configuration.ModernAlert;
import com.beautysalon.gate.Model.services.Atributet_sherbimeve;
import com.beautysalon.gate.Model.services.Sherbimet;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class servicedetails {
    
     
    @FXML
    private Label cmimiLabel;

    @FXML
    private TableColumn<Sherbimet, String> emriCol;

    @FXML
    private Label emriLabel;

    @FXML
    private TableView<Atributet_sherbimeve> atributetTable;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private Label idLabel;

    @FXML
    private TableColumn<?, ?> kohezgjatjaCol;

    @FXML
    private BorderPane pane;

    @FXML
    private TableColumn<?, ?> pershkrimiCol;

    @FXML
    private Label pershkrimiLabel;

    @FXML
    private Button rollbackBtn;

    @FXML
    private TableColumn<?, ?> zbritjaCol;

    @FXML
    private Label zbritjaLabel;

    @FXML
    private Label kohezgjatjaLabel;

    private Sherbimet sherbimi = SessionManager.getSherbimi();
    
    @FXML
    public void initialize(){
          
        if(sherbimi == null) ModernAlert.warning("Deshtim", "Nuk ka te dhena!");

         idLabel.setText(sherbimi.getID().toString());
         emriLabel.setText(sherbimi.getEmri_sherbimit());
         pershkrimiCol.setText(sherbimi.getPershkrimi());
         int totalSeconds = sherbimi.getKohezgjatja();

int hours = totalSeconds / 3600;
int minutes = (totalSeconds % 3600) / 60;
int seconds = totalSeconds % 60;

String formatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);

         kohezgjatjaLabel.setText(formatted);
         zbritjaLabel.setText(String.valueOf(sherbimi.getZbritja()));
         cmimiLabel.setText(String.valueOf(sherbimi.getQmimi_baze()));
    
         


    }
    
}
