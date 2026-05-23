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
       primaryresponse.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleUpoYkdjaU9pSklVekkxTmlKOS5leUp6ZFdJaU9pSmhaRzFwYmlJc0ltbGtJam96TENKeWIyeGxJam9pVWs5TVJWOUJSRTFKVGlJc0ltbGhkQ0k2TVRjM09UVTFOemMyTUN3aVpYaHdJam94TnpjNU5Ua3pOell3ZlEua2ZxM3ZNQndJR2xBM3NZSy1scHB4N0pPb2Z0UmE5Y3JZOVItSE4yaXZoUSIsImlkIjozLCJyb2xlIjoiUk9MRV9BRE1JTiIsInR5cGUiOiJDT01QQU5ZX0FDQ0VTUyIsImlhdCI6MTc3OTU1NzgxMSwiZXhwIjoxNzc5NTkzODExfQ.6Vmt6Gz6xEtWoMsdu2IKCKuT5e8XORfTqbTjuKTyMLI");
       primaryresponse.setUsername("admin");
       SessionManager.setPrimaryResponse(primaryresponse);
       StageManager.MainWindow();// */ 
    }

    public static void main(String[] args) {
        launch();
    }
}
