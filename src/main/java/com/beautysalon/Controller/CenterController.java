package com.beautysalon.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class CenterController {
        public static BorderPane mainpane;

    public static void setMainpane(BorderPane pane) {
        mainpane = pane;
    }

    public static void loadCenterContent(String fxmlPath) {
        try {
            BorderPane node = FXMLLoader.load(
                    CenterController.class.getResource("/fxml/" + fxmlPath)
            );

            if (mainpane != null) {
                mainpane.setCenter(node);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
