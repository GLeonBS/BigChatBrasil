package com.bigchatbrasil.modules.cliente.interfaces;

import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.mensagem.enums.Prioridade;

import java.math.BigDecimal;

public interface CheckSaldo {

    BigDecimal valorNormal = new BigDecimal("0.25");
    BigDecimal valorPrioritario = new BigDecimal("0.50");

    void verificaDescontaSaldoCliente(ClienteEntity client, Prioridade prioridade);

    void descontaSaldoCliente(ClienteEntity cliente, Prioridade prioridade, BigDecimal valor);

    PlanoEnum getPlano();
}
