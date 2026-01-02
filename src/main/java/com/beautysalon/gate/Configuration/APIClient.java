package com.beautysalon.gate.Configuration;


import java.net.http.HttpClient;
import java.time.Duration;

public class APIClient {

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private APIClient() {} 

    public static HttpClient getClient() {
        return CLIENT;
    }
}