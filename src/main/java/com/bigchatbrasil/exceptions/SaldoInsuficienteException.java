package com.bigchatbrasil.exceptions;

import java.math.BigDecimal;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String tipoSaldo, BigDecimal saldo) {
        super(tipoSaldo + " insuficiente: " + saldo);
    }
}
