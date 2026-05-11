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
    private Button clientsbutton;

    @FXML
    private Button profilebutton;

    @FXML
    private Button servicesbutton;

    @FXML
    private Button terminetbutton;
@FXML
private VBox sidebar;

@FXML
private Button toggleBtn;

private boolean pinned = false;
@FXML
public void initialize() {

    CenterController.setMainpane(mainpane);
    CenterController.loadCenterContent("clientsview.fxml");

    // START COLLAPSED
    sidebar.getStyleClass().add("collapsed");

    toggleBtn.setOnAction(e -> {
        pinned = !pinned;

        if (pinned) {
            sidebar.getStyleClass().remove("collapsed");
        } else {
            sidebar.getStyleClass().add("collapsed");
        }

        refreshLayout();
    });

    sidebar.setOnMouseEntered(e -> {
        if (!pinned) {
            sidebar.getStyleClass().remove("collapsed");
            refreshLayout();
        }
    });

    sidebar.setOnMouseExited(e -> {
        if (!pinned) {
            sidebar.getStyleClass().add("collapsed");
            refreshLayout();
        }
    });

    clientsbutton.setOnAction(e -> CenterController.loadCenterContent("clientsview.fxml"));
    servicesbutton.setOnAction(e -> CenterController.loadCenterContent("servicesview.fxml"));
    profilebutton.setOnAction(e -> CenterController.loadCenterContent("profileview.fxml"));
}
private void refreshLayout() {
    sidebar.applyCss();
    sidebar.layout();
}
}