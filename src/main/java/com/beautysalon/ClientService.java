package com.beautysalon;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientService {
private static final String API_URL = "http://localhost:8080/api/clients";
private static final String USERNAME = "sa";
private static final String PASSWORD = "11112222";

public List<Client> getClients() throws Exception {
    HttpClient client = HttpClient.newHttpClient();

    // Encode username:password in Base64
    String auth = Base64.getEncoder().encodeToString((USERNAME + ":" + PASSWORD).getBytes());

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(API_URL))
            .header("Authorization", "Basic " + auth) // <-- Add credentials here
            .GET()
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    // Optional: print status to debug
    System.out.println("Status code: " + response.statusCode());
    System.out.println("Response body: " + response.body());

    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(response.body(), new TypeReference<List<Client>>() {});
}
}