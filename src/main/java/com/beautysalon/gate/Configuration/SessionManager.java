package com.beautysalon.gate.Configuration;

import java.util.List;

import com.beautysalon.gate.Model.User;
import com.beautysalon.gate.Model.clients.Client;
import com.beautysalon.gate.Model.services.Sherbimet;

public class SessionManager {

    private SessionManager() {}

    private static String token;
    private static User currentUser;
    private static List<Sherbimet> sherbimet;
    private static List<Client> clients;

    
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

    public static void setUser(User user) {
        currentUser = user;
    }

    public static User getUser() {
        return currentUser;
    }

    public static boolean isAuthenticated() {
        return token != null && currentUser != null;
    }

    public static void clear() {
        token = null;
        currentUser = null;
        sherbimet = null;
        clients = null;
    }
}
