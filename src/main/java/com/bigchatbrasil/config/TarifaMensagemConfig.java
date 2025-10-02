package com.bigchatbrasil.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@Getter
public class TarifaMensagemConfig {

    @Value("${tarifa.mensagem.normal:0.25}")
    private BigDecimal valorNormal;

    @Value("${tarifa.mensagem.prioritaria:0.50}")
    private BigDecimal valorPrioritaria;
}