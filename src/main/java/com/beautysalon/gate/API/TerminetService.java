package com.beautysalon.gate.API;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.Configuration.APIConfig;
import com.beautysalon.gate.Configuration.APIGenericCalls;
import com.beautysalon.gate.Configuration.MapperProvider;
import com.beautysalon.gate.Configuration.APIConfig.TERMINET;
import com.beautysalon.gate.DTO.Terminet.TerminetGetDTO;
import com.fasterxml.jackson.core.type.TypeReference;

public class TerminetService {


    public static CompletableFuture<List<TerminetGetDTO>> fetchAppointmentsByEmployeeId(Long ID){
         return CompletableFuture.supplyAsync(()->{

            try{
                 
                HttpResponse<String> res = APIGenericCalls.sendRequest("GET", APIConfig.Get(TERMINET.GET_ID) + ID, true, null);

                return MapperProvider.getMapper().readValue(res.body(), new TypeReference<List<TerminetGetDTO>>() {});

            } catch(Exception e){
 
                throw new RuntimeException(e);
            }
         });
    }
}