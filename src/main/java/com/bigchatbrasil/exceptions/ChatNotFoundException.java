package com.bigchatbrasil.exceptions;

public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException() {
        super("Chat não encontrado!");
    }
}
