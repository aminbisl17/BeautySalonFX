package com.beautysalon.Backend.Controllers.terminet.viewTypes;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class weekCalController {

    @FXML
    private GridPane gridpane;

    private Map<String, StackPane> cellMap = new HashMap<>();

    private final int HOURS = 12;  // e.g., 8AM-8PM
    private final int DAYS = 7;    // Monday-Sunday
@FXML
public void initialize() {

  // addAppointment(2, 1, "Haircut - Anna");

    for (int day = 0; day < DAYS; day++) {
        for (int hour = 0; hour < HOURS; hour++) {
            StackPane slot = new StackPane();
            slot.setPrefSize(100, 50);
            slot.setStyle("-fx-border-color: lightgray; -fx-background-color: white;");
            gridpane.add(slot, day, hour);

            cellMap.put(day + "," + hour, slot);
        }
    }
}

public void addAppointment(int day, int hour, String text) {
    StackPane slot = cellMap.get(day + "," + hour);
    if (slot != null) {
        slot.setStyle("-fx-background-color: lightgreen; -fx-border-color: gray;");
        slot.getChildren().add(new Label(text));
    }
}

}

