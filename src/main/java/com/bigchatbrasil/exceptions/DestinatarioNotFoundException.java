package com.bigchatbrasil.exceptions;

public class DestinatarioNotFoundException extends RuntimeException {
    public DestinatarioNotFoundException() {
        super("Destinatário não encontrado!");
    }
}
