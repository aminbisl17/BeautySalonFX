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

    
}
