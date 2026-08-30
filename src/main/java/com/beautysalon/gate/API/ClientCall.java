package com.beautysalon.gate.API;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.Configuration.APIConfig;
import com.beautysalon.gate.Configuration.APIGenericCalls;
import com.beautysalon.gate.Configuration.MapperProvider;
import com.beautysalon.gate.Configuration.APIConfig.CLIENTS;
import com.beautysalon.gate.Configuration.APIConfig.category;
import com.beautysalon.gate.Exceptions.TokenException;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;
import com.beautysalon.gate.Model.clients.Client;
import com.beautysalon.gate.Model.clients.ClientHistory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientCall {


private static ObjectMapper mapper = MapperProvider.getMapper();

public static CompletableFuture<Boolean> fetchClients() {

    return CompletableFuture.supplyAsync(() -> {
System.out.println(APIConfig.Get(CLIENTS.ALL));
        try {
            HttpResponse<String> response =
                    APIGenericCalls.sendRequest(
                            "GET",
                            APIConfig.Get(CLIENTS.ALL),
                        //  APIConfig.get(category.CLIENTS).get("history")
                            true,
                            null
                    );

             SessionManager.setClients(mapper.readValue(
                    response.body(),
                    new TypeReference<List<Client>>() {}
            ));
            return true;

        } catch (TokenException te) {
    APIErrorHandler.handle(te);
    return false;
} catch (Exception e) {
  //  e.printStackTrace();
    throw new RuntimeException(e);
}
    });
}

public static CompletableFuture<List<ClientHistory>> fetchClientHistory(Long ID) {

    return CompletableFuture.supplyAsync(() -> {
        try { 
             HttpResponse<String> response = APIGenericCalls.sendRequest("GET", APIConfig.Get(CLIENTS.HISTORY) + ID, true, null);
             return mapper.readValue(response.body(),new TypeReference<List<ClientHistory>>() {});
        } catch (Exception ex) {
            APIErrorHandler.handle(ex);
            return null;
        }
    });
}

public static CompletableFuture<Boolean> updateClient(Client client){

   return CompletableFuture.supplyAsync(() -> {
        try {
    
       (APIGenericCalls.sendRequest("PUT", APIConfig.Get(CLIENTS.UPDATE), true, client)).body();
            return true;
        } catch (Exception ex) {
            APIErrorHandler.handle(ex);
            return false;
        }
    });

}


public static CompletableFuture<Boolean> deleteClient(Long ID){

   return CompletableFuture.supplyAsync(() -> {
        try {
            (APIGenericCalls.sendRequest("DELETE", APIConfig.Get(CLIENTS.DELETE), true, ID)).body();
            return true;
        } catch (Exception ex) {
            APIErrorHandler.handle(ex);
            return false;
        }
    });

}

}
