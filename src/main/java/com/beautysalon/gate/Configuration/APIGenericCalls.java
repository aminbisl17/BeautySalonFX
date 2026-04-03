package com.beautysalon.gate.Configuration;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.util.Map;

import com.beautysalon.gate.Exceptions.TokenException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APIGenericCalls extends HttpMethods{

    public String URL;

    public APIGenericCalls(String URL) {
        this.URL = URL;
        //super(URL);
    }

    @Override
public HttpResponse<String> getMethod(boolean auth)
        throws IOException, InterruptedException, TokenException {

    if (auth) validateToken();

    HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .GET();

    if (auth) {
        requestBuilder.header("Authorization", "Bearer " + token);
    }

    return APIClient.getClient().send(
            requestBuilder.build(),
            HttpResponse.BodyHandlers.ofString()
    );
}

@Override
public HttpResponse<String> postMethod(boolean auth, Map<String, String> values)
        throws TokenException, InterruptedException, IOException {

   if(auth)validateToken(); 

    HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(values)));

             requestBuilder.header("Content-Type", "application/json");
    if (auth) {
        requestBuilder.header("Authorization", "Bearer " + token);
    } 

    return APIClient.getClient().send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
}
    
}
