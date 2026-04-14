package com.beautysalon.gate.API;

import java.io.IOException;
import java.net.http.HttpResponse;

import com.beautysalon.gate.Configuration.APIGenericCalls;
import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.Exceptions.TokenException;

public class ServerAPI {
    

    public boolean isActive() throws IOException, InterruptedException, TokenException{

    //     HttpResponse<String> response = (new APIGenericCalls(SessionManager.URL[3][0])).getMethod(false);
         return ((new APIGenericCalls(SessionManager.URL[4][0])).getMethod(false)).statusCode() == 200 ? true : false;
         
    }
}
