package com.beautysalon.gate.Configuration;

import com.beautysalon.StageManager;
import javafx.stage.Stage;

public class ExpiredToken {


    public static void RedirectAfterExpire(){
        SessionManager.ClearToken();
        StageManager.getStage().close();
        StageManager.login();
    }
}
