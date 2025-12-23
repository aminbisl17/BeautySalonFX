package com.beautysalon.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class MapperProvider {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private MapperProvider() {} // prevent instantiation

    public static ObjectMapper getMapper() {
        return MAPPER;
    }
}