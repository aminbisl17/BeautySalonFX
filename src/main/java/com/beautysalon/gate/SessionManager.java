package com.beautysalon.gate;

import java.util.List;

import com.beautysalon.gate.Configuration.DotEnv;
import com.beautysalon.gate.Model.clients.Client;
import com.beautysalon.gate.Model.services.Sherbimet;
import com.beautysalon.gate.responses.loginResponse;

public class SessionManager {

    private SessionManager() {}

    private static String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleUpoYkdjaU9pSklVekkxTmlKOS5leUp6ZFdJaU9pSmhaRzFwYmlJc0ltbGtJam96TENKeWIyeGxJam9pVWs5TVJWOUJSRTFKVGlJc0ltbGhkQ0k2TVRjM09ETTBPVFUzTnl3aVpYaHdJam94TnpjNE16ZzFOVGMzZlEuZUNqbE1iZktCS1NNVnBoeUlkV2F4STVqc0NEWGRKVWJ6MEtWT0VrbGJ0YyIsImlkIjozLCJyb2xlIjoiUk9MRV9BRE1JTiIsInR5cGUiOiJDT01QQU5ZX0FDQ0VTUyIsImlhdCI6MTc3ODM0OTYxMiwiZXhwIjoxNzc4Mzg1NjEyfQ.oU5-dfssbn6mYPCeE_cZ_HigwbwDh0fuVqrfTi9aPkA";
    private static List<Sherbimet> sherbimet;
    private static List<Client> clients;
    private static Client client;
    private static loginResponse primaryResponse;

    public static String[][] URL = {
        {
            DotEnv.getDotEnv().get("API_SERVICES_ALL"),
            DotEnv.getDotEnv().get("API_SERVICES_ATRIBUTE")
        },
        {
            DotEnv.getDotEnv().get("API_CLIENTS_ALL"),
            DotEnv.getDotEnv().get("API_CLIENTS_HISTORY")
        },
        {
            DotEnv.getDotEnv().get("API_USER_DATA")
        },
        {
            DotEnv.getDotEnv().get("API_AUTHENTICATION_ATTENDANCE_GENERATE"),
            DotEnv.getDotEnv().get("API_AUTHENTICATION_ATTENDANCE_VALIDATE")
        },
        {
             DotEnv.getDotEnv().get("API_SERVER_HEALTH")
        },
        {
          DotEnv.getDotEnv().get("WS_AUTHENTICATION")
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
