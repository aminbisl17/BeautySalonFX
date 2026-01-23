package com.beautysalon.gate.API.Authentication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import javax.naming.AuthenticationException;

import com.beautysalon.gate.Configuration.APIClient;
import com.beautysalon.gate.Configuration.DotEnv;
import com.beautysalon.gate.Configuration.MapperProvider;
import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.Exceptions.ServerErrorException;
import com.beautysalon.gate.responses.loginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthService {

    private ObjectMapper MAPPER = MapperProvider.getMapper();
   // private String authapi = DotEnv.getDotEnv().get("API_AUTHENTICATION");

    public void login(String username, String password) throws ServerErrorException, IOException, InterruptedException, AuthenticationException {

    HttpResponse<String> response = APIClient.getClient().send(
         HttpRequest.newBuilder()
            .uri(URI.create(DotEnv.getDotEnv().get("API_AUTHENTICATION")))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString( MAPPER.writeValueAsString(
        Map.of("username", username, "password", password)
    )))
            .build(), HttpResponse.BodyHandlers.ofString());

    int code = response.statusCode();

    if (code == 401 || code == 403) {
        throw new  AuthenticationException("Invalid username or password");
    }

    if (code == 500) {
        throw new ServerErrorException("Internal server error");
    }

    if (code != 200) {
        throw new RuntimeException("Login failed");
    }

    loginResponse data = MAPPER.readValue(response.body(), loginResponse.class);

    SessionManager.setToken(data.getToken());
    SessionManager.setUser(data.getUser());

    System.out.println(SessionManager.getToken());

   // return MAPPER.readValue(response.body(), loginResponse.class);
}
}
