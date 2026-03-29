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
import com.beautysalon.gate.Model.services.Sherbimet;
import com.beautysalon.gate.responses.ServiceInfoResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServicesAPI{

    private ObjectMapper MAPPER = MapperProvider.getMapper();

    public void getAllSherbimet()throws ServerErrorException, IOException, InterruptedException, AuthenticationException, TokenException{

   
       HttpResponse<String> response = (new APIGenericCalls(SessionManager.URL[0][0])).getMethod();
        
    if (response.statusCode() == 401 || response.statusCode() == 403) {
        throw new TokenException();
    }

    if (response.statusCode() == 500) {
        throw new ServerErrorException("Internal server error");
    }

    if (response.statusCode() != 200) {
        throw new RuntimeException("Fetch failed");
    }

            
    SessionManager.setSherbimet(MAPPER.readValue(response.body(),new TypeReference<List<Sherbimet>>() {}));

    }

    public ServiceInfoResponse getAtributet_sherbimit(Long id)throws ServerErrorException, IOException, InterruptedException, AuthenticationException, TokenException{
   
       HttpResponse<String> response = (new APIGenericCalls(SessionManager.URL[0][1] + id)).getMethod();
        
    if (response.statusCode() == 401 || response.statusCode() == 403) {
        throw new TokenException();
    }

    if (response.statusCode() == 500) {
        throw new ServerErrorException("Internal server error");
    }

    if (response.statusCode() != 200) {
        throw new RuntimeException("Fetch failed");
    }
 

     return  MAPPER.readValue(response.body(), ServiceInfoResponse.class);
    }
}
