package com.beautysalon.gate.Configuration;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.time.Duration;

import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.Exceptions.ServerErrorException;
import com.beautysalon.gate.Exceptions.TokenException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APIGenericCalls {

    private static ObjectMapper mapper = MapperProvider.getMapper();

    private static HttpClient CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static HttpResponse<String> getMethod(boolean auth, String URL)
            throws IOException, InterruptedException, TokenException {

        if (auth)
            validateToken();

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .GET();

        requestBuilder.header("Content-Type", "application/json");
        if (auth) {
            requestBuilder.header("Authorization", "Bearer " + SessionManager.getToken());
        }

        return CLIENT.send(
                requestBuilder.build(),
                HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> postMethod(boolean auth, String URL, Map<String, String> values)
            throws TokenException, InterruptedException, IOException {

        if (auth)
            validateToken();

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(values)));

        requestBuilder.header("Content-Type", "application/json");
        if (auth) {
            requestBuilder.header("Authorization", "Bearer " + SessionManager.getToken());
        }

        return CLIENT.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> putMethod(boolean auth, String URL, Object body)
            throws TokenException, InterruptedException, IOException {

        if (auth)
            validateToken();

        String jsonBody = MapperProvider.getMapper().writeValueAsString(body);

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody));

        requestBuilder.header("Content-Type", "application/json");

        if (auth) {
            requestBuilder.header("Authorization", "Bearer " + SessionManager.getToken());
        }

        return CLIENT.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> deleteMethod(boolean auth, String URL)
            throws IOException, InterruptedException, TokenException {

        if (auth)
            validateToken();

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .DELETE();

        requestBuilder.header("Content-Type", "application/json");

        if (auth) {
            requestBuilder.header("Authorization", "Bearer " + SessionManager.getToken());
        }

        return CLIENT.send(
                requestBuilder.build(),
                HttpResponse.BodyHandlers.ofString());
    }

    private static void validateToken() throws TokenException {
        String token = SessionManager.getToken();
        if (token == null || token.isEmpty()) {
            throw new TokenException();
        }
    }

public static HttpResponse<String> sendRequest(
        String method,
        String url,
        boolean auth,
        Object body
) throws IOException, InterruptedException, TokenException, ServerErrorException {

    if (auth) validateToken();

    HttpRequest.Builder builder = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .timeout(Duration.ofSeconds(10))
            .header("Content-Type", "application/json");

    if (auth) {
        builder.header("Authorization", "Bearer " + SessionManager.getToken());
    }

    switch (method) {
        case "GET" -> builder.GET();
        case "DELETE" -> builder.DELETE();
        case "POST" -> builder.POST(HttpRequest.BodyPublishers.ofString(
                body == null ? "" : mapper.writeValueAsString(body)
        ));
        case "PUT" -> builder.PUT(HttpRequest.BodyPublishers.ofString(
                body == null ? "" : mapper.writeValueAsString(body)
        ));
        default -> throw new IllegalArgumentException("Invalid method: " + method);
    }

    HttpResponse<String> response =
            CLIENT.send(builder.build(), HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() == 401 || response.statusCode() == 403) {
        throw new TokenException();
    }

    if (response.statusCode() == 500) {
        throw new ServerErrorException("Internal server error");
    }

    if (response.statusCode() != 200) {
        throw new RuntimeException("Fetch failed: " + response.statusCode());
    }

    return response;
}
}
