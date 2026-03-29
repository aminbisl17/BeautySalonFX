package com.beautysalon.gate.API;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

import javax.naming.AuthenticationException;

import com.beautysalon.gate.Configuration.APIGenericCalls;
import com.beautysalon.gate.Configuration.DotEnv;
import com.beautysalon.gate.Configuration.MapperProvider;
import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.Exceptions.ServerErrorException;
import com.beautysalon.gate.Exceptions.TokenException;
import com.beautysalon.gate.responses.loginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthAPI {

    private ObjectMapper MAPPER = MapperProvider.getMapper();

    public void login(String username, String password) throws ServerErrorException, IOException, InterruptedException, AuthenticationException, TokenException {

 HttpResponse<String> response = (new APIGenericCalls(DotEnv.getDotEnv().get("API_AUTHENTICATION"))).postMethod(false, Map.of(
    "username", username,
    "password", password
));

     if (response.statusCode() == 401 || response.statusCode() == 403) {
        throw new TokenException();
    }

    if (response.statusCode() == 500) {
        throw new ServerErrorException("Internal server error");
    }

    if (response.statusCode() != 200) {
        throw new RuntimeException("Login Failed");
    }
    loginResponse data = MAPPER.readValue(response.body(), loginResponse.class);
    SessionManager.setToken(data.getToken());
    SessionManager.setUser(data.getUser());
}
}
