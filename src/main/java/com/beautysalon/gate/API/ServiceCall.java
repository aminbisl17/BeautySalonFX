package com.beautysalon.gate.API;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.Configuration.APIConfig;
import com.beautysalon.gate.Configuration.APIGenericCalls;
import com.beautysalon.gate.Configuration.MapperProvider;
import com.beautysalon.gate.Exceptions.ServerErrorException;
import com.beautysalon.gate.Exceptions.TokenException;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;
import com.beautysalon.gate.Model.clients.Client;
import com.beautysalon.gate.Model.services.Atributet_sherbimeve;
import com.beautysalon.gate.Model.services.Sherbimet;
import com.beautysalon.gate.responses.ServiceInfoResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.beautysalon.gate.Configuration.APIConfig.SERVICES;
import com.beautysalon.gate.Configuration.APIConfig.category;
import com.beautysalon.gate.DTO.SherbimetUpdateDTO;

public class ServiceCall {
    

    private static ObjectMapper mapper = MapperProvider.getMapper();

    public static CompletableFuture<Boolean> fetchServices(){
            
               return CompletableFuture.supplyAsync(() -> { try {
                    HttpResponse<String> response = 
                    APIGenericCalls.sendRequest(
                        "GET",
                         APIConfig.Get(SERVICES.ALL),
                          true, null);

 SessionManager.setSherbimet(mapper.readValue(response.body(),new TypeReference<List<Sherbimet>>() {}));
                    return true;
                }
                catch(TokenException e){
                    APIErrorHandler.handle(e);
                    return false;
                }
                 catch (Exception e) {
            
                  //  e.printStackTrace();
                  throw new RuntimeException(e);
                //  return false;
    }
            });
    }

public static CompletableFuture<ServiceInfoResponse> fetchServiceAtributes(Long ID) {
    return CompletableFuture.supplyAsync(() -> {
        try {
            HttpResponse<String> response =
                    APIGenericCalls.sendRequest(
                            "GET",
                            APIConfig.Get(SERVICES.ATTRIBUTES) + ID,
                            true,
                            null
                    );

            return mapper.readValue(response.body(), ServiceInfoResponse.class);

        } catch (TokenException e) {
            APIErrorHandler.handle(e);
            return null;

        } catch (Exception e) {
        //    e.printStackTrace();
        throw new RuntimeException(e);

        }
    });
}

public static CompletableFuture<Boolean> updateServices(SherbimetUpdateDTO sherbimet, Long ID) {

    return CompletableFuture.supplyAsync(() -> {

        try {

            if (true) {
               String token = SessionManager.getToken();
        if (token == null || token.isEmpty()) {
            throw new TokenException();
        }
            }

            String url = APIConfig.Get(SERVICES.UPDATE)+ ID;

                   // System.out.println(url);

            String boundary = "----JavaBoundary" + System.currentTimeMillis();

            String json = mapper.writeValueAsString(sherbimet);

            ByteArrayOutputStream output = new ByteArrayOutputStream();

            // data part
            output.write((
                    "--" + boundary + "\r\n" +
                    "Content-Disposition: form-data; name=\"data\"\r\n" +
                    "Content-Type: application/json\r\n\r\n" +
                    json + "\r\n"
            ).getBytes(StandardCharsets.UTF_8));

            // end multipart
            output.write((
                    "--" + boundary + "--\r\n"
            ).getBytes(StandardCharsets.UTF_8));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .header(
                            "Content-Type",
                            "multipart/form-data; boundary=" + boundary
                    )
                    .header(
                            "Authorization",
                            "Bearer " + SessionManager.getToken()
                )
                    .method(
                            "PATCH",
                            HttpRequest.BodyPublishers.ofByteArray(
                                    output.toByteArray()
                            )
                    )
                    .build();

            HttpResponse<String> response = APIGenericCalls.CLIENT.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

           // System.out.println("token: " + SessionManager.getToken());
           // System.out.println(response.statusCode());

            if (response.statusCode() == 401 ||
                response.statusCode() == 403) {
                throw new TokenException();
            }

            if (response.statusCode() == 500) {
                throw new ServerErrorException(
                        "Internal server error"
                );
            }

            if (response.statusCode() != 200) {
                throw new RuntimeException(
                        "Update failed: " + response.statusCode()
                );
            }

            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    });
}

public static CompletableFuture<Boolean> deleteService(Long ID){
  return CompletableFuture.supplyAsync(()->{
        try{
       
            HttpResponse<String> req = APIGenericCalls.sendRequest("DELETE", APIConfig.Get(SERVICES.DELETE) + ID, true, null);

       //     System.out.println( APIConfig.Get(SERVICES.DELETE) + ID + " " + req.statusCode());

            return req.statusCode() == 200 ? true : false;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    });
}
}
