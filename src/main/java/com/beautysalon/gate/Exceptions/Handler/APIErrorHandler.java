package com.beautysalon.gate.Exceptions.Handler;

import com.beautysalon.StageManager;
import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.Configuration.ModernAlert;
import com.beautysalon.gate.Exceptions.TokenException;

import javafx.application.Platform;
public final class APIErrorHandler {

    private APIErrorHandler() {}

    private static void RedirectAfterExpire(){
        SessionManager.ClearToken();
        StageManager.getStage().close();
        StageManager.login();
    }
    public static void handle(Throwable ex) {

        if (ex instanceof TokenException) {
            Platform.runLater(() -> {
            //    Alert("Session expired", ex.getMessage());
                ModernAlert.danger("Session expired!", "");
                RedirectAfterExpire();
            });
            return;
        }

        Platform.runLater(() -> {
            ModernAlert.danger("Error", ex.getMessage());
        });
    }
}
