package com.beautysalon.gate;

import java.util.List;

import com.beautysalon.gate.Configuration.DotEnv;
import com.beautysalon.gate.Model.clients.Client;
import com.beautysalon.gate.Model.services.Sherbimet;

public class SessionManager {

    private SessionManager() {}

    private static String token;
    private static List<Sherbimet> sherbimet;
    private static List<Client> clients;


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

    public static void clear() {
        token = null;
        sherbimet = null;
        clients = null;
    }
}
