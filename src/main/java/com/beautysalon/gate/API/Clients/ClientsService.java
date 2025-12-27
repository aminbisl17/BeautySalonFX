package com.beautysalon.gate.API.Clients;

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
import com.beautysalon.gate.Model.clients.Client;

public class ClientsService {
    

    public List<Client> fetchAllClients() throws ServerErrorException, IOException, InterruptedException, AuthenticationException{

          String token = SessionManager.getToken();

        if(token == null || token.isEmpty()){
              throw new AuthenticationException("Access token expired");
        }

        HttpResponse<String> response = APIClient.getClient().send(
            HttpRequest.newBuilder().uri(URI.create("http://localhost:8000/api/employee/clients/all"))
        .header("Authorization","Bearer " + token)
        .GET().build(), HttpResponse.BodyHandlers.ofString());

        
          if (response.statusCode() == 401 || response.statusCode() == 403) {
        throw new  AuthenticationException("Invalid username or password");
       }

    if (response.statusCode() == 500) {
        throw new ServerErrorException("Internal server error");
    }

    if (response.statusCode() != 200) {
        throw new RuntimeException("Login failed");
    }

         return MapperProvider.getMapper().readValue(
    response.body(),
    new com.fasterxml.jackson.core.type.TypeReference<List<Client>>() {}
);
    }
}
