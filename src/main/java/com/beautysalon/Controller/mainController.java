package com.beautysalon.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class mainController {

    @FXML
    private StackPane centerpane;

    @FXML
    private Button clientsbutton;

    @FXML
    private Button profilebutton;

    @FXML
    private Button servicesbutton;

    @FXML
    private BorderPane sidebar;

    @FXML
    private Button terminetbutton;

    @FXML
    public void initialize(){

        Button[] buttons = new Button[]{profilebutton, terminetbutton, clientsbutton, servicesbutton};

        sidebar.setOnMouseEntered(e -> {
            Timeline expand = new Timeline(
                    new KeyFrame(Duration.millis(200),
                            new KeyValue(sidebar.prefWidthProperty(), 200))
            );
            expand.play();

           for(Button b : buttons){
              b.setVisible(true);
           }
        });

        sidebar.setOnMouseExited(e -> {
            Timeline collapse = new Timeline(
                    new KeyFrame(Duration.millis(200),
                            new KeyValue(sidebar.prefWidthProperty(), 50))
            );
            collapse.play();

             for(Button b : buttons){
              b.setVisible(false);
           }
        });
    }

}
