package com.backoffice.modules.cliente.vo;

import java.math.BigDecimal;

import com.backoffice.modules.cliente.enums.PlanoEnum;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Embeddable
public class Conta {

    @Getter
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
