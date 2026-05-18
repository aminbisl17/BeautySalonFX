package com.beautysalon.gate.Configuration;

import java.util.Map;

public class APIEndpoints {

    private final Map<String, String> endpoints;

    public APIEndpoints(Map<String, String> endpoints) {
        this.endpoints = endpoints;
    }

    public String get(String key) {
        return endpoints.get(key);
    }
}