package com.beautysalon.gate.Exceptions.Handler;

import com.beautysalon.StageManager;
import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.Exceptions.TokenException;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public final class APIErrorHandler {

    private APIErrorHandler() {}

    private static void RedirectAfterExpire(){
        SessionManager.ClearToken();
        StageManager.getStage().close();
        StageManager.login();
    }

    private static void Alert(String title, String message){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(title);
                alert.setContentText(message);
                alert.showAndWait();
    }

    public static void handle(Throwable ex) {

        if (ex instanceof TokenException) {
            Platform.runLater(() -> {
                Alert("Session expired", ex.getMessage());
                RedirectAfterExpire();
            });
            return;
        }

        Platform.runLater(() -> {
            Alert("Error", ex.getMessage());
        });
    }
}
