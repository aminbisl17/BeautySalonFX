package com.beautysalon.gate.Configuration;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ModernAlert {

    public static Optional<ButtonType> confirm(String title, String message) {
        return build(Alert.AlertType.CONFIRMATION, title, message, "confirm-dialog")
                .showAndWait();
    }

    public static Optional<ButtonType> info(String title, String message) {
        return build(Alert.AlertType.INFORMATION, title, message, "info-dialog")
                .showAndWait();
    }

    public static Optional<ButtonType> warning(String title, String message) {
        return build(Alert.AlertType.WARNING, title, message, "warning-dialog")
                .showAndWait();
    }

    public static Optional<ButtonType> danger(String title, String message) {
        return build(Alert.AlertType.CONFIRMATION, title, message, "danger-dialog")
                .showAndWait();
    }

    public static Optional<ButtonType> success(String title, String message) {
    return build(Alert.AlertType.INFORMATION, title, message, "success-dialog")
            .showAndWait();
}

 private static Alert build(Alert.AlertType type, String title, String message, String styleClass) {

    Alert alert = new Alert(type);

    // remove JavaFX default header
    alert.setHeaderText(null);

    DialogPane pane = alert.getDialogPane();

    // remove default content padding behavior
    pane.setContentText(message);

    // attach CSS
    pane.getStylesheets().add(
            ModernAlert.class.getResource("/css/AlertCss.css").toExternalForm()
    );

    pane.getStyleClass().add(styleClass);

    // create custom header
    Label customTitle = new Label(title);
    customTitle.getStyleClass().add("dialog-title");

    VBox headerBox = new VBox(customTitle);
    headerBox.getStyleClass().add("dialog-header");

    pane.setHeader(headerBox);

    return alert;
}
}