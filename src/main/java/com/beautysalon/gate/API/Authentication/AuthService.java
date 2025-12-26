package com.beautysalon.gate.API.Authentication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import javax.naming.AuthenticationException;

import com.beautysalon.gate.API.APIClient;
import com.beautysalon.gate.Configuration.MapperProvider;
import com.beautysalon.gate.Exceptions.ServerErrorException;
import com.beautysalon.gate.responses.loginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthService {

    private static final ObjectMapper MAPPER = MapperProvider.getMapper();

    public loginResponse login(String username, String password) throws ServerErrorException, IOException, InterruptedException, AuthenticationException {

    String json = MAPPER.writeValueAsString(
        Map.of("username", username, "password", password)
    );

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8000/auth/login/employee"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

    HttpResponse<String> response = APIClient.getClient().send(request, HttpResponse.BodyHandlers.ofString());

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

    return MAPPER.readValue(response.body(), loginResponse.class);
}
}
