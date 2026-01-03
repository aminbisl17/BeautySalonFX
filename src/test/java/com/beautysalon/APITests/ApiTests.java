package com.beautysalon.APITests;

import com.beautysalon.gate.API.Clients.ClientsService;
import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.Model.clients.Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.naming.AuthenticationException;

public class ApiTests {

    private ClientsService clients;

    @BeforeEach
    void setup() {
        clients = new ClientsService();

        // IMPORTANT: token must exist for API calls
        SessionManager.setToken("PUT_A_VALID_TEST_TOKEN_HERE");
    }

    @Test
    void testFetchAllClientsSuccess() throws Exception {

     
    }

    @Test
    void testFetchClientsUnauthorized() {

        // simulate expired token
        SessionManager.setToken(null);

        assertThrows(AuthenticationException.class, () -> {
            clients.fetchAllClients();
        });
    }
}
