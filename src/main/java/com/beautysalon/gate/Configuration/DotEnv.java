package com.beautysalon.gate.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

public class DotEnv {

    private static Dotenv dotenv;

    public static void init(){
       dotenv = Dotenv.load();
    }

    public static Dotenv getDotEnv(){
        return dotenv;
    }


}
