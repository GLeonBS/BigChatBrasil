package com.bigchatbrasil.modules.cliente.dto;

import java.math.BigDecimal;

import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;

import lombok.Builder;

@Builder
public record ContaRequestDTO(PlanoEnum plano, BigDecimal credito, BigDecimal limite, BigDecimal limiteConsumido) {
}
