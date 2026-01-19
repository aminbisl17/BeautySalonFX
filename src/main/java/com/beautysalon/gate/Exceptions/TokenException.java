package com.beautysalon.gate.Exceptions;

public class TokenException extends Exception {
     public TokenException(){
        super("Your session has expired!");
     }
}
