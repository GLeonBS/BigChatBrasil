package com.bigchatbrasil.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Cliente não encontrado!");
    }
}
