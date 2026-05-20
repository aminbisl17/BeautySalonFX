package com.beautysalon.gate.API;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.Configuration.APIConfig;
import com.beautysalon.gate.Configuration.APIGenericCalls;
import com.beautysalon.gate.Configuration.MapperProvider;
import com.beautysalon.gate.Exceptions.ServerErrorException;
import com.beautysalon.gate.Exceptions.TokenException;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;
import com.beautysalon.gate.Model.services.Sherbimet;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.beautysalon.gate.Configuration.APIConfig.category;

public class ServiceCall {
    

    private static ObjectMapper mapper = MapperProvider.getMapper();

    public static CompletableFuture<Boolean> fetchServices(){
            
               return CompletableFuture.supplyAsync(() -> { try {
                    HttpResponse<String> response = 
                    APIGenericCalls.sendRequest(
                        "GET",
                         APIConfig.get(category.SERVICES).get("all"),
                          true, null);

 SessionManager.setSherbimet(mapper.readValue(response.body(),new TypeReference<List<Sherbimet>>() {}));
                    return true;
                }
                catch( TokenException e){
                    APIErrorHandler.handle(e);
                    return false;
                }
                 catch (IOException | InterruptedException | ServerErrorException e) {
            
                  //  e.printStackTrace();
                  throw new RuntimeException(e);
                //  return false;
    }});
            
       
    }
}
