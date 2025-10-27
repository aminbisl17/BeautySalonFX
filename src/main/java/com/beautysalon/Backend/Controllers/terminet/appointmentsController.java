package com.beautysalon.Backend.Controllers.terminet;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class appointmentsController {

    @FXML
    private StackPane stackpane;

    @FXML
    private BorderPane weekCal;

    @FXML
    public void initialize(){

      try {
        GridPane pane = (GridPane) (new FXMLLoader(getClass().getResource("/fxml/terminet/scheduleCalendars/weekCal.fxml"))).load();
//
  //      if (pane instanceof Region region) {
     //       region.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
       // }
       weekCal.setCenter(pane);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

}
