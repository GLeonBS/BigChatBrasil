package com.bigchatbrasil.modules.cliente.interfaces;

import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.mensagem.enums.PrioridadeEnum;

import java.math.BigDecimal;

public interface CheckSaldo {

    void verificaDescontaSaldoCliente(ClienteEntity client, PrioridadeEnum prioridade);

    void descontaSaldoCliente(ClienteEntity cliente, PrioridadeEnum prioridade, BigDecimal valor);

    PlanoEnum getPlano();

    BigDecimal getValorNormal();

    BigDecimal getValorPrioritario();
}
