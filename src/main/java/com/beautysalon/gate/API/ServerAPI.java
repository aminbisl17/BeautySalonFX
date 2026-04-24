package com.beautysalon.gate.API;

import java.io.IOException;

import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.Configuration.APIGenericCalls;
import com.beautysalon.gate.Exceptions.TokenException;

public class ServerAPI {
    

    public boolean isActive() throws IOException, InterruptedException, TokenException{

    //     HttpResponse<String> response = (new APIGenericCalls(SessionManager.URL[3][0])).getMethod(false);
         return (( APIGenericCalls.getMethod(false, SessionManager.URL[4][0]))).statusCode() == 200 ? true : false;
         
    }
}
