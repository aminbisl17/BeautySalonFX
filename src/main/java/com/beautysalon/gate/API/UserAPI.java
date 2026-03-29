package com.beautysalon.gate.API;

import java.io.IOException;
import java.net.http.HttpResponse;

import javax.naming.AuthenticationException;

import com.beautysalon.gate.Configuration.APIGenericCalls;
import com.beautysalon.gate.Configuration.MapperProvider;
import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.Exceptions.ServerErrorException;
import com.beautysalon.gate.Exceptions.TokenException;
import com.beautysalon.gate.Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserAPI {
    
    private ObjectMapper MAPPER = MapperProvider.getMapper();

      public void getUserData()throws ServerErrorException, IOException, InterruptedException, AuthenticationException, TokenException{
   
       HttpResponse<String> response = (new APIGenericCalls(SessionManager.URL[2][0])).getMethod();
        
    if (response.statusCode() == 401 || response.statusCode() == 403) {
        throw new TokenException();
    }

    if (response.statusCode() == 500) {
        throw new ServerErrorException("Internal server error");
    }

    if (response.statusCode() != 200) {
        throw new RuntimeException("Fetch failed");
    }
 
      SessionManager.setUser(MAPPER.readValue(response.body(), User.class));

    }
}
