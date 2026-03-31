package com.beautysalon.gate.Configuration;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

import com.beautysalon.gate.Exceptions.TokenException;

public abstract class HttpMethods {

    protected String URL;
    protected String token = SessionManager.getToken();

    public HttpMethods(String URL){
            this.URL = URL;
    }

     
    protected void validateToken() throws TokenException {
        if (token == null || token.isEmpty()) {
            throw new TokenException();
        }
    }

     public abstract HttpResponse<String> getMethod(boolean auth) throws TokenException, InterruptedException, IOException;

     public abstract HttpResponse<String> postMethod(boolean auth, Map<String,String> values) throws TokenException, InterruptedException, IOException;
}
