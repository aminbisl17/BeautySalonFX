package com.beautysalon;


import java.io.IOException;

import javax.naming.AuthenticationException;

import org.glassfish.grizzly.http.server.Session;

import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.Configuration.DotEnv;
import com.beautysalon.gate.Exceptions.ServerErrorException;
import com.beautysalon.gate.responses.loginResponse;

import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException, AuthenticationException, ServerErrorException, InterruptedException {
         DotEnv.init();
         StageManager.init(stage);
        // StageManager.login();

       loginResponse primaryresponse = new loginResponse();
      primaryresponse.setRole("ROLE_ADMIN");
       primaryresponse.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleUpoYkdjaU9pSklVekkxTmlKOS5leUp6ZFdJaU9pSmhaRzFwYmlJc0ltbGtJam96TENKeWIyeGxJam9pVWs5TVJWOUJSRTFKVGlJc0ltbGhkQ0k2TVRjNE9ESXdNRGcxTnl3aVpYaHdJam94TnpnNE1qZzNNalUzZlEuc25rc3JvN3dvd1dvMDJ0QTZnQkFfaDdzdXNDdmtDVWw2Q1BUd2JBbTU4dyIsImlkIjozLCJyb2xlIjoiUk9MRV9BRE1JTiIsInR5cGUiOiJDT01QQU5ZX0FDQ0VTUyIsImlhdCI6MTc4ODIwMDg4NiwiZXhwIjoxNzg4Mjg3Mjg2fQ.JNiGWRUzYg-g3XuyM9T6D8RKvEJRLWbrGkTNTcC5pS4");
       primaryresponse.setUsername("admin");
       SessionManager.setPrimaryResponse(primaryresponse);
       StageManager.MainWindow();// */ 
    }

    public static void main(String[] args) {
        launch();
    }
}
