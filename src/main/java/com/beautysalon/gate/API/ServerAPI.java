package com.beautysalon.gate.API;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.Configuration.APIConfig;
import com.beautysalon.gate.Configuration.APIConfig.SERVER;
import com.beautysalon.gate.Configuration.APIConfig.category;
import com.beautysalon.gate.Configuration.APIGenericCalls;
import com.beautysalon.gate.Exceptions.TokenException;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;
import com.beautysalon.gate.Model.services.Sherbimet;

public class ServerAPI {
    

       public static CompletableFuture<Boolean> fetchServerHealth(){
            
               return CompletableFuture.supplyAsync(() -> { try {
                    HttpResponse<String> response = 
                    APIGenericCalls.sendRequest(
                        "GET",
                         APIConfig.Get(SERVER.HEALTH),
                          false, null);

                    return response.statusCode() == 200 ? true : false;
                }
                catch(TokenException e){
                    APIErrorHandler.handle(e);
                    return false;
                }
                 catch (Exception e) {
            
                //    e.printStackTrace();
                  throw new RuntimeException(e);
                  
    }
            });
    }

    public boolean isActive() throws IOException, InterruptedException, TokenException{

    //     HttpResponse<String> response = (new APIGenericCalls(SessionManager.URL[3][0])).getMethod(false);
         return (( APIGenericCalls.getMethod(false, SessionManager.URL[4][0]))).statusCode() == 200 ? true : false;
         
    }
}
