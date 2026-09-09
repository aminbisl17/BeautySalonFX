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
     //    StageManager.login();

       loginResponse primaryresponse = new loginResponse();
    primaryresponse.setRole("ROLE_EMPLOYEE");
       primaryresponse.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWQiOjEwMTUsInJvbGUiOiJST0xFX0VNUExPWUVFIiwidHlwZSI6IkNPTVBBTllfQUNDRVNTIiwiaWF0IjoxNzg4OTgwMzI5LCJleHAiOjE3ODkwNjY3Mjl9.v6MmFNnEWNljKFmmQ4h86K_BXC_UZqMX3a6ZzuK1YwM");
       primaryresponse.setUsername("admin");
       SessionManager.setPrimaryResponse(primaryresponse);
       StageManager.MainWindow();// */ 
    }

    public static void main(String[] args) {
        launch();
    }
}
