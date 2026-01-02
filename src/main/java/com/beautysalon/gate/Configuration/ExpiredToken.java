package com.beautysalon.gate.Configuration;

import com.beautysalon.StageManager;
import javafx.stage.Stage;

public class ExpiredToken {
    
    public static void RedirectAfterExpire(Stage stage){
        SessionManager.ClearToken();
         stage.close();
        StageManager.login();
    }
}
