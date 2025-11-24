package com.beautysalon.Backend.Controllers.terminet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.beautysalon.Backend.Controllers.terminet.viewTypes.ScheduleBuilder;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class appointmentsController {

  @FXML
    private GridPane gridpane;

    @FXML
    private VBox leftpane;

    @FXML
    private StackPane stackpane;

    @FXML
    private BorderPane weekCal;

     private Map<String, StackPane> cellMap = new HashMap<>();

    private final int HOURS = 12;  // e.g., 8AM-8PM
    private final int DAYS = 7; 

    @FXML
    public void initialize(){

       gridpane.getStylesheets().add(
        getClass().getResource("/css/EmployeeDashboard/terminet/week1.css").toExternalForm()
    );

        for (int day = 0; day < DAYS; day++) {

            for (int hour = 0; hour < HOURS; hour++) {

                StackPane slot = new StackPane();
                slot.setPrefSize(100, 50);
                slot.getStyleClass().add("slot");

                // Hover effect
                slot.setOnMouseEntered(e -> slot.getStyleClass().add("slot-hover"));
                slot.setOnMouseExited(e -> slot.getStyleClass().remove("slot-hover"));

                // Click to toggle highlight
                slot.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    if (slot.getStyleClass().contains("slot-highlighted")) {
                        slot.getStyleClass().remove("slot-highlighted");
                    } else {
                        slot.getStyleClass().add("slot-highlighted");
                    }
                });

                gridpane.add(slot, day, hour);
                cellMap.put(day + "," + hour, slot);
            }
        }

        // Example appointment
        addAppointment(2, 1, "Haircut - Anna");
         addAppointment(1, 3, "Haircut - Amin");
         
    }

    public void addAppointment(int day, int hour, String text) {
        StackPane slot = cellMap.get(day + "," + hour);
        if (slot != null) {
            Label label = new Label(text);
            label.getStyleClass().add("appointment-label");
            slot.getChildren().add(label);
            leftpane.getChildren().add(new Label((String.valueOf(hour))));

            if (!slot.getStyleClass().contains("slot-appointment")) {
                slot.getStyleClass().add("slot-appointment");
            }
        }
    }

    public void highlightCell(int day, int hour) {
        StackPane slot = cellMap.get(day + "," + hour);
        if (slot != null && !slot.getStyleClass().contains("slot-highlighted")) {
            slot.getStyleClass().add("slot-highlighted");
        }
    }


}
