package com.beautysalon.gate.API;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import javax.naming.AuthenticationException;

import com.beautysalon.gate.Configuration.APIGenericCalls;
import com.beautysalon.gate.Configuration.MapperProvider;
import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.Exceptions.ServerErrorException;
import com.beautysalon.gate.Exceptions.TokenException;
import com.beautysalon.gate.Model.clients.Client;
import com.beautysalon.gate.Model.clients.ClientHistory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientsAPI {
    
    private ObjectMapper mapper = MapperProvider.getMapper();

    public void fetchAllClients() throws ServerErrorException, IOException, InterruptedException, TokenException{

        HttpResponse<String> response =  APIGenericCalls.getMethod(true, SessionManager.URL[1][0]);

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
 
        HttpResponse<String> response = APIGenericCalls.getMethod(true, SessionManager.URL[1][1] + ID);

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
