package com.bigchatbrasil.exceptions;

public class PlanoNotFoundException extends RuntimeException {
    public PlanoNotFoundException() {
        super("Plano não encontrado!");
    }
}
