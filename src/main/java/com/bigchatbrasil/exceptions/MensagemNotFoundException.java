package com.bigchatbrasil.exceptions;

public class MensagemNotFoundException extends RuntimeException {
    public MensagemNotFoundException() {
        super("Mensagem não encontrada!");
    }
}
