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
       primaryresponse.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleUpoYkdjaU9pSklVekkxTmlKOS5leUp6ZFdJaU9pSmhaRzFwYmlJc0ltbGtJam96TENKeWIyeGxJam9pVWs5TVJWOUJSRTFKVGlJc0ltbGhkQ0k2TVRjM09EWTVOemd4TXl3aVpYaHdJam94TnpjNE56TXpPREV6ZlEuZVdzQjNWY3FSdWo0OEM0Uk43dVFQU3dUaG92WWpLRFo3MVpRVXFkV3VMcyIsImlkIjozLCJyb2xlIjoiUk9MRV9BRE1JTiIsInR5cGUiOiJDT01QQU5ZX0FDQ0VTUyIsImlhdCI6MTc3ODY5Nzg1MSwiZXhwIjoxNzc4NzMzODUxfQ.Ggc4D3O450LjRoPZRC2aUb9Y3lQeKye4bj-_JhBoj_o");
       primaryresponse.setUsername("admin");
       SessionManager.setPrimaryResponse(primaryresponse);
       StageManager.MainWindow(); 
    }

    public static void main(String[] args) {
        launch();
    }
}
