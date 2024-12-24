package com.backoffice.modules.cliente.entity;

import java.math.BigDecimal;

import com.backoffice.modules.cliente.enums.PlanoEnum;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Embeddable
public class Conta {

    @NotNull
    @Enumerated(EnumType.STRING)
    private PlanoEnum plano;
    private BigDecimal credito = BigDecimal.ZERO;
    private BigDecimal limite = BigDecimal.ZERO;

    public BigDecimal getSaldo() {
        if (PlanoEnum.PRE_PAGO.equals(plano)) {
            return credito;
        } else {
            return limite;
        }
    }
}
