package com.bigchatbrasil.config;

import java.math.BigDecimal;
import java.util.UUID;

import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.vo.Conta;

public class Fixtures {

    public static ClienteEntity createCliente(UUID id) {
        Conta conta = new Conta(PlanoEnum.PRE_PAGO, new BigDecimal("100.00"), BigDecimal.ZERO);

        return new ClienteEntity(id, "Leon", "Leon@leon.com",
                "44999999999", "12345678901", "12345678901234",
                "Leon LTDA", conta);
    }
}
