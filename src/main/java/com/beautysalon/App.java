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
       primaryresponse.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleUpoYkdjaU9pSklVekkxTmlKOS5leUp6ZFdJaU9pSmhaRzFwYmlJc0ltbGtJam96TENKeWIyeGxJam9pVWs5TVJWOUJSRTFKVGlJc0ltbGhkQ0k2TVRjM09EZzNNVGd5TWl3aVpYaHdJam94TnpjNE9UQTNPREl5ZlEuTGRadWNXMEJUSW5wMU5vTERtMDlENWtfWnliZ2xOX3ZKc2xSbjhyMlIzcyIsImlkIjozLCJyb2xlIjoiUk9MRV9BRE1JTiIsInR5cGUiOiJDT01QQU5ZX0FDQ0VTUyIsImlhdCI6MTc3ODg3MTg5OSwiZXhwIjoxNzc4OTA3ODk5fQ.YF7aHpQkHr7m_9C1IARR-lnWGgfAu9grBf-yE3srH8Y");
       primaryresponse.setUsername("admin");
       SessionManager.setPrimaryResponse(primaryresponse);
       StageManager.MainWindow(); 
    }

    public static void main(String[] args) {
        launch();
    }
}
