package com.beautysalon.Database;

import org.junit.jupiter.api.Test;

import com.beautysalon.Backend.Database.Database;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    void testDatabaseConnection() {

         Database.connect();

    }
}
