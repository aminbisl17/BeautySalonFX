package com.beautysalon.Backend.Controllers.klientet.dialogues;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class clientProfileFormController {

    @FXML
    private TextField dataregF;

    @FXML
    private TextField emriF;

    @FXML
    private Button fshijBtn;

    @FXML
    private ComboBox<String> gjiniaF;

    @FXML
    private TextField mbiemriF;

    @FXML
    private TextField nrtelF;

    @FXML
    private TextArea pershkrimiF;

    @FXML
    private Button saveBtn;

    @FXML 
    private void initialize(){
        gjiniaF.getItems().addAll("Mashkull", "Femer", "Asnjejes");
    }

    public void setData(String emri, String mbiemri, String nrtel, char gjinia, String pershkrimi, Timestamp datareg){
        emriF.setText(emri);
        mbiemriF.setText(mbiemri);
        nrtelF.setText(nrtel);
        String gj = (Character.toLowerCase(gjinia)) == 'M' ? "Mashkull" : (Character.toLowerCase(gjinia) == 'F') ? "Femer" : "Asnjejes";
        if(gjiniaF.getItems().contains(gj)){
            gjiniaF.setValue(gj);
        }
        pershkrimiF.setText(pershkrimi);
        dataregF.setText(datareg.toLocalDateTime()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

}
