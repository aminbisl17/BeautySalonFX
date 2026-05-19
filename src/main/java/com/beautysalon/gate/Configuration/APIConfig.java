package com.beautysalon.gate.Configuration;

import java.util.Map;
import io.github.cdimascio.dotenv.Dotenv;

public class APIConfig {
        private static final Dotenv env = DotEnv.getDotEnv();

        public static enum category {
    SERVICES,
    CLIENTS,
    USER,
    AUTHENTICATION,
    SERVER,
    WEBSOCKET
}

    private static String require(String key) {
        String value = env.get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                "Missing environment variable: " + key
            );
        }

        return value;
    }

    private static final Map<category, APIEndpoints> endpoints =
            Map.of(

            category.CLIENTS,
            new APIEndpoints(Map.of(
                    "all", require("API_CLIENTS_ALL"),
                    "history", require("API_CLIENTS_HISTORY"),
                    "update", require("API_CLIENTS_UPDATE"),
                    "delete", require("API_CLIENTS_DELETE")
            )),

            category.SERVICES,
            new APIEndpoints(Map.of(
                    "all", require("API_SERVICES_ALL"),
                    "attribute", require("API_SERVICES_ATTRIBUTE")
            )),

            category.USER,
            new APIEndpoints(Map.of(
                    "data", require("API_USER_DATA")
            ))
    );

    public static APIEndpoints get(category apicategory) {
        return endpoints.get(apicategory);
    }
}
