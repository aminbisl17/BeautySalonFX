package com.beautysalon.gate;

import java.util.List;

import com.beautysalon.gate.Configuration.DotEnv;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;
import com.beautysalon.gate.Model.clients.Client;
import com.beautysalon.gate.Model.services.Sherbimet;
import com.beautysalon.gate.responses.loginResponse;

import io.github.cdimascio.dotenv.Dotenv;

public class SessionManager {

    private SessionManager() {}

    private static String token;
    private static List<Sherbimet> sherbimet;
    private static List<Client> clients;
    private static Client client;
    private static loginResponse primaryResponse;
    private final static Dotenv dotenv = DotEnv.getDotEnv();

    public static String getAPI(String path){
        try{
         return DotEnv.getDotEnv().get(path);
        } catch(Exception e){
             e.printStackTrace();
            APIErrorHandler.handle(e);
        }
        return null;
    }

    public static String[][] URL = {
        {
            dotenv.get("API_SERVICES_ALL"),
            dotenv.get("API_SERVICES_ATRIBUTE")
        },
        {
            dotenv.get("API_CLIENTS_ALL"),
            dotenv.get("API_CLIENTS_HISTORY"),
            dotenv.get("API_CLIENTS_UPDATE"),
            dotenv.get("API_CLIENTS_DELETE")
        },
        {
            dotenv.get("API_USER_DATA")
        },
        {
            dotenv.get("API_AUTHENTICATION_ATTENDANCE_GENERATE"),
            dotenv.get("API_AUTHENTICATION_ATTENDANCE_VALIDATE")
        },
        {
            dotenv.get("API_SERVER_HEALTH")
        },
        {
            dotenv.get("WS_AUTHENTICATION")
        }
    };

    
    public static List<Client> getClients() {
        return clients;
    }

    public static void setClients(List<Client> clients) {
        SessionManager.clients = clients;
    }

    public static List<Sherbimet> getSherbimet() {
        return sherbimet;
    }

    public static void setSherbimet(List<Sherbimet> sherbimet) {
        SessionManager.sherbimet = sherbimet;
    }

    public static void setToken(String jwt) {
        token = jwt;
    }

    public static String getToken() {
        return token;
    }

    public static void ClearToken(){
         token = null;
    }

    public static boolean isAuthenticated() {
        return token != null;
    }

    
    public static loginResponse getPrimaryResponse() {
        return primaryResponse;
    }

        public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        SessionManager.client = client;
    }


    public static void setPrimaryResponse(loginResponse primaryresponse) {
         primaryResponse = primaryresponse;
         token = primaryresponse.getToken();
    }

    public static void clear() {
        primaryResponse = null;
        token = null;
        sherbimet = null;
        clients = null;
        client = null;
    }
}
