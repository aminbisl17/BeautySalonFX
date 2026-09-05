package com.beautysalon.gate.Configuration;

import java.sql.ClientInfoStatus;
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

public static enum CLIENTS {
    ALL,
    HISTORY,
    UPDATE,
    DELETE
}

public static enum SERVICES {
    ALL,
    ATTRIBUTES,
    UPDATE,
    DELETE,
    REGISTER
}

public static enum USER{
    DATA
}

public static enum SERVER{
    HEALTH
}
public static enum AUTHENTICATION{
    GENERATE,
    WS
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

    private static final Map<Object, String> test =
  
    Map.ofEntries(
    Map.entry(CLIENTS.ALL, require("API_CLIENTS_ALL")),
    Map.entry(CLIENTS.HISTORY, require("API_CLIENTS_HISTORY")),
    Map.entry(CLIENTS.UPDATE, require("API_CLIENTS_UPDATE")),
    Map.entry(CLIENTS.DELETE, require("API_CLIENTS_DELETE")),

    Map.entry(SERVICES.ALL, require("API_SERVICES_ALL")),
    Map.entry(SERVICES.ATTRIBUTES, require("API_SERVICES_ATTRIBUTE")),
    Map.entry(SERVICES.UPDATE, require("API_SERVICES_UPDATE")),
    Map.entry(SERVICES.DELETE, require("API_SERVICES_DELETE")),
    Map.entry(SERVICES.REGISTER, require("API_SERVICES_REGISTER")),

    Map.entry(USER.DATA, require("API_USER_DATA")),

    Map.entry(AUTHENTICATION.GENERATE, require("API_AUTHENTICATION_ATTENDANCE_GENERATE")),
    Map.entry(AUTHENTICATION.WS, require("WS_AUTHENTICATION")),
    Map.entry(SERVER.HEALTH, require("API_SERVER_HEALTH"))
);

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
                    "attribute", require("API_SERVICES_ATTRIBUTE"),
                    "update", require("API_SERVICES_UPDATE")
            )),

            category.USER,
            new APIEndpoints(Map.of(
                    "data", require("API_USER_DATA")
            )),
            category.SERVER, new APIEndpoints(Map.of(
                "health", require("API_SERVER_HEALTH")
            )),

            category.AUTHENTICATION,
            new APIEndpoints(
                Map.of("generate", require("API_AUTHENTICATION_ATTENDANCE_GENERATE"))
            )
    );

    public static APIEndpoints get(category apicategory) {
        return endpoints.get(apicategory);
    }
    public static String Get(Object ca){
        return test.get(ca);
    }
}
