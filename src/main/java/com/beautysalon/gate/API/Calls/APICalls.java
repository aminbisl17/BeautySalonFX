package com.beautysalon.gate.API.Calls;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.Configuration.APIEndpoints;
import com.beautysalon.gate.Configuration.APIGenericCalls;
import com.beautysalon.gate.Configuration.DotEnv;
import com.beautysalon.gate.Configuration.MapperProvider;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;
import com.beautysalon.gate.Model.clients.Client;
import com.beautysalon.gate.Model.clients.ClientHistory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cdimascio.dotenv.Dotenv;

public class APICalls {

      private final static Dotenv env = DotEnv.getDotEnv();

   public enum ApiCategory {

    SERVICES(new APIEndpoints(Map.of(
            "all", env.get("API_SERVICES_ALL"),
            "attribute", env.get("API_SERVICES_ATTRIBUTE")
    ))),

    CLIENTS(new APIEndpoints(Map.of(
            "all", env.get("API_CLIENTS_ALL"),
            "history", env.get("API_CLIENTS_HISTORY"),
            "update", env.get("API_CLIENTS_UPDATE"),
            "delete", env.get("API_CLIENTS_DELETE")
    ))),

    USER(new APIEndpoints(Map.of(
            "data", env.get("API_USER_DATA")
    ))),

    AUTHENTICATION(new APIEndpoints(Map.of(
            "generate", env.get("API_AUTHENTICATION_ATTENDANCE_GENERATE"),
            "validate", env.get("API_AUTHENTICATION_ATTENDANCE_VALIDATE")
    ))),

    SERVER(new APIEndpoints(Map.of(
            "health", env.get("API_SERVER_HEALTH")
    ))),

    WEBSOCKET(new APIEndpoints(Map.of(
            "auth", env.get("WS_AUTHENTICATION")
    )));

    private final APIEndpoints endpoints;

    ApiCategory(APIEndpoints endpoints) {
        this.endpoints = endpoints;
    }

    public APIEndpoints endpoints() {
        return endpoints;
    }
}

private static ObjectMapper mapper = MapperProvider.getMapper();

public static CompletableFuture<Boolean> fetchClients() {

    return CompletableFuture.supplyAsync(() -> {


        try {
            String url = ApiCategory.CLIENTS.endpoints().get("all");

            System.out.println("URL = " + url);

            HttpResponse<String> response = APIGenericCalls.getMethod(true, url);
                   // APIGenericCalls.sendRequest("GET", url, true, null);

            System.out.println("STATUS = " + response.statusCode());
            System.out.println("BODY = " + response.body());
           SessionManager.setClients(mapper.readValue(response.body(),new TypeReference<List<Client>>() {}));
            return true;

        } catch (Exception ex) {
            ex.printStackTrace(); // IMPORTANT
            return false;
        }
    });
}


public static CompletableFuture<List<ClientHistory>> fetchClientHistory(Long ID) {

    return CompletableFuture.supplyAsync(() -> {
        try { 
             HttpResponse<String> response = APIGenericCalls.sendRequest("GET", ApiCategory.CLIENTS.endpoints().get("history"), true, ID);
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
       //     HttpResponse<String> response = APIGenericCalls.sendRequest("PUT", ApiCategory.CLIENTS.endpoints().get("update"), true, client);
       System.out.println( ApiCategory.CLIENTS.endpoints().get("update"));
       (APIGenericCalls.sendRequest("PUT", ApiCategory.CLIENTS.endpoints().get("update"), true, client)).body();
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
            (APIGenericCalls.sendRequest("DELETE", ApiCategory.CLIENTS.endpoints().get("delete"), true, ID)).body();
            return true;
        } catch (Exception ex) {
            APIErrorHandler.handle(ex);
            return false;
        }
    });

}

}
