package com.beautysalon.gate.API.Calls;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.API.ClientsAPI;
import com.beautysalon.gate.Configuration.ExecutorConfig;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;

public class APICalls {

    public enum ApiCategory {

    SERVICES(
        System.getenv("API_SERVICES_ALL"),
        System.getenv("API_SERVICES_ATRIBUTE")
    ),

    CLIENTS(
        System.getenv("API_CLIENTS_ALL"),
        System.getenv("API_CLIENTS_HISTORY"),
        System.getenv("API_CLIENTS_UPDATE"),
        System.getenv("API_CLIENTS_DELETE")
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
}

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
}
