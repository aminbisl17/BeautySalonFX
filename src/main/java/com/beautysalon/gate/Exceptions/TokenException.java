package com.beautysalon.gate.Exceptions;


import javafx.scene.control.Alert;

public class TokenException extends Exception {
     public TokenException(){
        super("Your session has expired!");
     }
}
