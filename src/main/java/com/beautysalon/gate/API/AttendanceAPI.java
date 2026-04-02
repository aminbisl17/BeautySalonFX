package com.beautysalon.gate.API;

import java.io.IOException;
import java.net.http.HttpResponse;

import com.beautysalon.gate.Configuration.APIGenericCalls;
import com.beautysalon.gate.Configuration.MapperProvider;
import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.Exceptions.ServerErrorException;
import com.beautysalon.gate.Exceptions.TokenException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AttendanceAPI {
    
    private ObjectMapper MAPPER = MapperProvider.getMapper();

public String GenerateAttendaceCode() throws TokenException, IOException, InterruptedException, ServerErrorException {


    HttpResponse<String> response = (new APIGenericCalls(SessionManager.URL[3][0])).getMethod(false);
    
      if (response.statusCode() == 401 || response.statusCode() == 403) {

        throw new TokenException();
    }

    if (response.statusCode() == 500) {
        throw new ServerErrorException("Internal server error");
    }

    if (response.statusCode() != 200) {
        throw new RuntimeException("Fetch failed");
    }

    try {
        JsonNode node = MAPPER.readTree(response.body());
        return node.get("code").asText(); 
    } catch (Exception e) {
        throw new RuntimeException("Failed to parse response", e);
    }
}
}
