package com.beautysalon;


import java.io.IOException;

import javax.naming.AuthenticationException;

import com.beautysalon.gate.Exceptions.ServerErrorException;

import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException, AuthenticationException, ServerErrorException, InterruptedException {
         
         StageManager.init(stage);
         StageManager.login();
    }

    public static void main(String[] args) {
        launch();
    }
}
