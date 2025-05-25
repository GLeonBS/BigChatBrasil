package com.bigchatbrasil.exceptions;

public class EstrategiaEnvioNotFoundException extends RuntimeException {
    public EstrategiaEnvioNotFoundException() {
        super("Estratégia de envio não encontrada!");
    }
}
