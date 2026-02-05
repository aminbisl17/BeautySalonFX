package com.beautysalon.gate.API.Services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import javax.naming.AuthenticationException;
import com.beautysalon.gate.API.HttpMethods;
import com.beautysalon.gate.API.APIGenericCalls;
import com.beautysalon.gate.Configuration.APIClient;
import com.beautysalon.gate.Configuration.DotEnv;
import com.beautysalon.gate.Configuration.MapperProvider;
import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.Exceptions.ServerErrorException;
import com.beautysalon.gate.Exceptions.TokenException;
import com.beautysalon.gate.Model.clients.ClientHistory;
import com.beautysalon.gate.Model.services.Atributet_sherbimeve;
import com.beautysalon.gate.Model.services.Sherbimet;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServicesService{
    

    private String[] URL = {
          DotEnv.getDotEnv().get("API_SERVICES_ALL"),
          DotEnv.getDotEnv().get("API_SERVICES_ATRIBUTE")

    };

    private ObjectMapper MAPPER = MapperProvider.getMapper();
    private String token = SessionManager.getToken();

    public void getAllSherbimet()throws ServerErrorException, IOException, InterruptedException, AuthenticationException, TokenException{

        if(token == null || token.isEmpty()){
              throw new TokenException(); //AuthenticationException("Access token expired");
        }

        HttpResponse<String> response = APIClient.getClient().send(
            HttpRequest.newBuilder().uri(URI.create(URL[0]))
        .header("Authorization","Bearer " + token)
        .GET().build(), HttpResponse.BodyHandlers.ofString());

          if (response.statusCode() == 401 || response.statusCode() == 403) {
        throw new  TokenException();
    }

    if (response.statusCode() == 500) {
        throw new ServerErrorException("Internal server error");
    }

    if (response.statusCode() != 200) {
        throw new RuntimeException("Login failed");
    }
            
    SessionManager.setSherbimet(MAPPER.readValue(response.body(),new TypeReference<List<Sherbimet>>() {}));

    }

    public List<Atributet_sherbimeve> getAtributet_sherbimit(Long id)throws ServerErrorException, IOException, InterruptedException, AuthenticationException, TokenException{
   
       HttpResponse<String> response = (new APIGenericCalls(URL[1] + id)).getMethod();
        
    if (response.statusCode() == 401 || response.statusCode() == 403) {
        System.out.println("here");
        throw new  TokenException();
    }

    if (response.statusCode() == 500) {
        throw new ServerErrorException("Internal server error");
    }

    if (response.statusCode() != 200) {
        throw new RuntimeException("Login failed");
    }


     return MAPPER.readValue(response.body(),new TypeReference<List<Atributet_sherbimeve>>() {});
    }
}
