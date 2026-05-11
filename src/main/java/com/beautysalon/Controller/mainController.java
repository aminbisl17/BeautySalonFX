package com.beautysalon.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class mainController {

    @FXML
    private BorderPane mainpane;

    @FXML
    private VBox sidebar;

    @FXML
    private VBox sidebarContent;

    @FXML
    private Button toggleBtn;

    @FXML
    private Button clientsbutton;

    @FXML
    private Button profilebutton;

    @FXML
    private Button servicesbutton;

    @FXML
    private Button terminetbutton;

    private boolean expanded = false;

    @FXML
    public void initialize() {

        CenterController.setMainpane(mainpane);
        CenterController.loadCenterContent("clientsview.fxml");

        // START COLLAPSED
        sidebar.setPrefWidth(70);
        sidebarContent.setVisible(false);
        sidebarContent.setManaged(false);

        toggleBtn.setOnAction(e -> toggleSidebar());

        clientsbutton.setOnAction(e ->
                CenterController.loadCenterContent("clientsview.fxml"));

        servicesbutton.setOnAction(e ->
                CenterController.loadCenterContent("servicesview.fxml"));

        profilebutton.setOnAction(e ->
                CenterController.loadCenterContent("profileview.fxml"));
    }

    private void toggleSidebar() {

        double start = expanded ? 220 : 70;
        double end = expanded ? 70 : 220;

        Timeline timeline = new Timeline();

        KeyValue kv = new KeyValue(sidebar.prefWidthProperty(), end);

        KeyFrame kf = new KeyFrame(Duration.millis(250), kv);

        timeline.getKeyFrames().add(kf);

        if (!expanded) {
            sidebarContent.setVisible(true);
            sidebarContent.setManaged(true);
        }

        timeline.setOnFinished(event -> {

            if (expanded) {
                sidebarContent.setVisible(false);
                sidebarContent.setManaged(false);
            }

            expanded = !expanded;
        });

        timeline.play();
    }
}