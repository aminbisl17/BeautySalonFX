package com.beautysalon.API.Authentication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import com.beautysalon.API.APIClient;
import com.beautysalon.API.responses.loginResponse;
import com.beautysalon.Configuration.MapperProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthService {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final ObjectMapper MAPPER = MapperProvider.getMapper();

    public loginResponse login(String username, String password) throws Exception {

    String json = MAPPER.writeValueAsString(
        Map.of("username", username, "password", password)
    );

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8000/auth/login/employee"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

    HttpResponse<String> response = APIClient.getClient().send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() == 401) {
        throw new RuntimeException("Invalid username or password");
    }

    if (response.statusCode() != 200) {
        throw new RuntimeException("Login failed");
    }

    return MAPPER.readValue(response.body(), loginResponse.class);
}
}
