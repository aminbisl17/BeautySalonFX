package com.beautysalon.gate.API.Clients;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import javax.naming.AuthenticationException;

import com.beautysalon.gate.API.APIGenericCalls;
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

    public void fetchAllClients() throws ServerErrorException, IOException, InterruptedException, TokenException{

        HttpResponse<String> response = (new APIGenericCalls(api_clients[0])).getMethod();

          if (response.statusCode() == 401 || response.statusCode() == 403) {
        throw new TokenException();
    }

    if (response.statusCode() == 500) {
        throw new ServerErrorException("Internal server error");
    }

    if (response.statusCode() != 200) {
        throw new RuntimeException("Fetch failed");
    }


    SessionManager.setClients(mapper.readValue(response.body(),new TypeReference<List<Client>>() {}));

    }

    public List<ClientHistory> getClientHistory(Long ID) throws InterruptedException, AuthenticationException, ServerErrorException, IOException, TokenException{
 
        HttpResponse<String> response = (new APIGenericCalls(api_clients[1] + ID)).getMethod();

          if (response.statusCode() == 401 || response.statusCode() == 403) {
        throw new TokenException();
    }

    if (response.statusCode() == 500) {
        throw new ServerErrorException("Internal server error");
    }

    if (response.statusCode() != 200) {
        throw new RuntimeException("Fetch failed");
    }

  //  String body = response.body();
   // if (body == null || body.isBlank()) {
    //    return Collections.emptyList(); // NEVER return null
   // }

        return mapper.readValue(response.body(),new TypeReference<List<ClientHistory>>() {});
    }
}
