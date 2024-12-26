package com.bigchatbrasil.modules.cliente.interfaces;

import java.math.BigDecimal;

import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;

public interface CheckSaldo {

    BigDecimal valorPadrao = new BigDecimal("0.25");

    void verificaDescontaSaldoCliente(ClienteEntity cliente);

    void descontaSaldoCliente(ClienteEntity cliente);

    PlanoEnum getPlano();
}
