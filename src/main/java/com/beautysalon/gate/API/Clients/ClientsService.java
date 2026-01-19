package com.beautysalon.gate.API.Clients;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

import javax.naming.AuthenticationException;

import com.beautysalon.gate.Configuration.APIClient;
import com.beautysalon.gate.Configuration.DotEnv;
import com.beautysalon.gate.Configuration.MapperProvider;
import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.Exceptions.ServerErrorException;
import com.beautysalon.gate.Exceptions.TokenException;
import com.beautysalon.gate.Model.clients.Client;
import com.beautysalon.gate.Model.clients.ClientHistory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientsService {
    
    private ObjectMapper mapper = MapperProvider.getMapper();

    private String[] api_clients = {
          DotEnv.getDotEnv().get("API_CLIENTS_ALL"),
          DotEnv.getDotEnv().get("API_CLIENTS_HISTORY")
    }; 

    private  String token = SessionManager.getToken();

    public void fetchAllClients() throws ServerErrorException, IOException, InterruptedException, TokenException{

        if(token == null || token.isEmpty()){
              throw new TokenException();
        }

        HttpResponse<String> response = APIClient.getClient().send(
            HttpRequest.newBuilder().uri(URI.create(api_clients[0]))
        .header("Authorization","Bearer " + token)
        .GET().build(), HttpResponse.BodyHandlers.ofString());

   
         if (response.statusCode() == 401 || response.statusCode() == 403) {
        throw new TokenException();
    }

    if (response.statusCode() == 500) {
        throw new ServerErrorException("Internal server error");
    }

    SessionManager.setClients(mapper.readValue(response.body(),new TypeReference<List<Client>>() {}));

    }

    public List<ClientHistory> getClientHistory(Long ID) throws InterruptedException, AuthenticationException, ServerErrorException, IOException{
 
        HttpResponse<String> response = APIClient.getClient().send(
            HttpRequest.newBuilder().uri(URI.create(api_clients[1]+ID))
        .header("Authorization","Bearer " + token)
        .GET().build(), HttpResponse.BodyHandlers.ofString());

        int code = response.statusCode();

         
          if (code == 401 || code == 403) {
        throw new  AuthenticationException("Invalid token");
       }

    if (code == 500) {
        throw new ServerErrorException("Internal server error");
    }

     String body = response.body();
    if (body == null || body.isBlank()) {
        return Collections.emptyList(); // NEVER return null
    }

        return mapper.readValue(response.body(),new TypeReference<List<ClientHistory>>() {});
    }
}
