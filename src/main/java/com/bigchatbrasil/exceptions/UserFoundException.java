package com.bigchatbrasil.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException() {
        super("Já existe um cliente com o mesmo email/cpf/cnpj");
    }
}
