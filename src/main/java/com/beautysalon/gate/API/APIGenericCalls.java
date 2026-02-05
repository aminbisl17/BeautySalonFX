package com.beautysalon.gate.API;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.beautysalon.gate.Configuration.APIClient;
import com.beautysalon.gate.Exceptions.TokenException;

public class APIGenericCalls extends HttpMethods{

    public APIGenericCalls(String URL) {
        super(URL);
    }

    @Override
    public HttpResponse<String> getMethod() throws IOException, InterruptedException, TokenException {
            validateToken();
             return APIClient.getClient().send(
            HttpRequest.newBuilder().uri(URI.create(URL))
           .header("Authorization","Bearer " + token)
           .GET().build(), HttpResponse.BodyHandlers.ofString());
 
    
    }
    
}
