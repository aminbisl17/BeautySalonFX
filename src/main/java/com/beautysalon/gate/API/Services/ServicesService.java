package com.beautysalon.gate.API.Services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import javax.naming.AuthenticationException;

import com.beautysalon.gate.API.APIClient;
import com.beautysalon.gate.API.SessionManager;
import com.beautysalon.gate.Configuration.MapperProvider;
import com.beautysalon.gate.Exceptions.ServerErrorException;
import com.beautysalon.gate.Model.Sherbimet;
import com.beautysalon.gate.responses.loginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServicesService {
    private static final ObjectMapper MAPPER = MapperProvider.getMapper();

    public List<Sherbimet> getAllSherbimet()throws ServerErrorException, IOException, InterruptedException, AuthenticationException{

        String token = SessionManager.getToken();

        if(token == null || token.isEmpty()){
              throw new AuthenticationException("Access token expired");
        }
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8000/api/mixed/sherbimet/all"))
        .header("Authorization","Bearer " + token)
        .GET().build();

        HttpResponse<String> response = APIClient.getClient().send(request, HttpResponse.BodyHandlers.ofString());

          if (response.statusCode() == 401 || response.statusCode() == 403) {
        throw new  AuthenticationException("Invalid username or password");
    }

    if (response.statusCode() == 500) {
        throw new ServerErrorException("Internal server error");
    }

    if (response.statusCode() != 200) {
        throw new RuntimeException("Login failed");
    }
            
        return MAPPER.readValue(
    response.body(),
    new com.fasterxml.jackson.core.type.TypeReference<List<Sherbimet>>() {}
);
    }
}
