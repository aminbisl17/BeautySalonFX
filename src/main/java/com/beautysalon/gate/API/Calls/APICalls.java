package com.beautysalon.gate.API.Calls;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.beautysalon.gate.API.ClientsAPI;
import com.beautysalon.gate.Configuration.DotEnv;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;
import com.beautysalon.gate.Model.clients.Client;
import com.beautysalon.gate.Model.clients.ClientHistory;

import io.github.cdimascio.dotenv.Dotenv;

public class APICalls {

    private static Dotenv env = DotEnv.getDotEnv();

    public enum ApiCategory {

    SERVICES(
        System.getenv("API_SERVICES_ALL"),
        System.getenv("API_SERVICES_ATRIBUTE")
    ),

    CLIENTS(
       // System.getenv("API_CLIENTS_ALL"),
        env.get("API_CLIENTS_ALL"),
        env.get("API_CLIENTS_HISTORY"),
        env.get("API_CLIENTS_UPDATE"),
        env.get("API_CLIENTS_DELETE")
    ),

    USER(
        System.getenv("API_USER_DATA")
    ),

    AUTHENTICATION(
        System.getenv("API_AUTHENTICATION_ATTENDANCE_GENERATE"),
        System.getenv("API_AUTHENTICATION_ATTENDANCE_VALIDATE")
    ),

    SERVER(
        System.getenv("API_SERVER_HEALTH")
    ),

    WEBSOCKET(
        System.getenv("WS_AUTHENTICATION")
    );

    private final String[] urls;

    ApiCategory(String... urls) {
        this.urls = urls;
    }

    public String[] getUrls() {
        return urls;
    }
}

// Clients
static ClientsAPI clientsService = new ClientsAPI();

/* 
   public static <T> void executeAsync(
        Callable<T> work,
        Consumer<T> onSuccess,
        Consumer<Throwable> onError
) {

    Task<T> task = new Task<>() {
        @Override
        protected T call() throws Exception {
            return work.call();
        }
    };

    task.setOnSucceeded(e -> {
        if (onSuccess != null) {
            onSuccess.accept(task.getValue());
        }
    });

    task.setOnFailed(e -> {
        if (onError != null) {
            onError.accept(task.getException());
        } else {
            APIErrorHandler.handle(task.getException());
        }
    });

    ExecutorConfig.submit(task);
}*/

public static CompletableFuture<Boolean> fetchClients() {

    return CompletableFuture.supplyAsync(() -> {
        try {
            clientsService.fetchAllClients();
            return true;
        } catch (Exception ex) {
            APIErrorHandler.handle(ex);
            return false;
        }
    });
}


public static CompletableFuture<List<ClientHistory>> fetchClientHistory(Long ID) {

    return CompletableFuture.supplyAsync(() -> {
        try {
            return clientsService.getClientHistory(ID);
        } catch (Exception ex) {
            APIErrorHandler.handle(ex);
            return null;
        }
    });
}

public static CompletableFuture<Boolean> updateClient(Client client){

   return CompletableFuture.supplyAsync(() -> {
        try {
            clientsService.updateClient(client);
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
            clientsService.deleteClient(ID);
            return true;
        } catch (Exception ex) {
            APIErrorHandler.handle(ex);
            return false;
        }
    });

}

}
