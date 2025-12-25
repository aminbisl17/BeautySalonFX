package com.beautysalon.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class mainController {

     @FXML
    private BorderPane mainpane;

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

        loadCenterContent("/fxml/clientsview.fxml");

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

        clientsbutton.setOnAction(e -> loadCenterContent("/fxml/clientsview.fxml"));
     //   servicesbutton.setOnAction(e -> loadCenterContent("/com/beautysalon/fxml/ServicesView.fxml"));
       // profilebutton.setOnAction(e -> loadCenterContent("/com/beautysalon/fxml/ProfileView.fxml"));

    }

      private void loadCenterContent(String fxmlPath) {
        try {
            BorderPane node = FXMLLoader.load(getClass().getResource(fxmlPath));
        //     node.prefWidthProperty().bind(mainpane.widthProperty());
          //  node.prefHeightProperty().bind(mainpane.heightProperty());
            mainpane.setCenter(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
