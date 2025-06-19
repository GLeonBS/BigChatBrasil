package com.bigchatbrasil.modules.mensagem.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrioridadeEnum {
    
    URGENTE(10),
    NORMAL(1);

    private final int nivel;
}
