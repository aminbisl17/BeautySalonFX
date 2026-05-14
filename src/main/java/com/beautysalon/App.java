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
      //   StageManager.login();

      loginResponse primaryresponse = new loginResponse();
      primaryresponse.setRole("ROLE_ADMIN");
       primaryresponse.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleUpoYkdjaU9pSklVekkxTmlKOS5leUp6ZFdJaU9pSmhaRzFwYmlJc0ltbGtJam96TENKeWIyeGxJam9pVWs5TVJWOUJSRTFKVGlJc0ltbGhkQ0k2TVRjM09EYzROREl3TXl3aVpYaHdJam94TnpjNE9ESXdNakF6ZlEuTjg5S1VxeHhMdFFNZS1qTFBCZEVlUTNVVVF3T0R5Y3hRZXdVT3NfLW51OCIsImlkIjozLCJyb2xlIjoiUk9MRV9BRE1JTiIsInR5cGUiOiJDT01QQU5ZX0FDQ0VTUyIsImlhdCI6MTc3ODc4NDI0MCwiZXhwIjoxNzc4ODIwMjQwfQ.qJ8g7O5KUPJjq8UGJLXFfwbW3fDJm7ARziYThHx5kI0");
       primaryresponse.setUsername("admin");
       SessionManager.setPrimaryResponse(primaryresponse);
       StageManager.MainWindow(); 
    }

    public static void main(String[] args) {
        launch();
    }
}
