package com.beautysalon.Backend.Controllers.terminet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.beautysalon.Backend.Controllers.terminet.viewTypes.ScheduleBuilder;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class appointmentsController {

    @FXML
    private StackPane stackpane;

        @FXML
    private VBox leftpane;


    @FXML
    private BorderPane weekCal;

    
private List<String> activeCells = new ArrayList<>();

    @FXML
    public void initialize(){

      try {
        GridPane pane = (GridPane) (new FXMLLoader(getClass().getResource("/fxml/terminet/scheduleCalendars/weekCal.fxml"))).load();
 // Example data
    activeCells.add("Mon_1_09:30_2025-10-29T09:00_123_AppointmentX_...");
    activeCells.add("Tue_1_11:00_2025-10-29T11:00_456_AppointmentY_...");

    ScheduleBuilder builder = new ScheduleBuilder(pane, leftpane, activeCells);
      builder.buildSchedule(LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfMonth()), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
       weekCal.setCenter(pane);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

}
