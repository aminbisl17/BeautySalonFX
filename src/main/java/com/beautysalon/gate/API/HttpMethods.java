package com.beautysalon.gate.API;

import java.io.IOException;
import java.net.http.HttpResponse;

import com.beautysalon.gate.Configuration.SessionManager;
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

     public abstract HttpResponse<String> getMethod() throws TokenException, InterruptedException, IOException ;
}
