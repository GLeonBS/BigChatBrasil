package com.bigchatbrasil.modules.mensagem.config;

import com.bigchatbrasil.modules.mensagem.enums.PrioridadeEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PrioridadeEnumConverter implements AttributeConverter<PrioridadeEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PrioridadeEnum attribute) {
        return attribute != null ? attribute.getNivel() : null;
    }

    @Override
    public PrioridadeEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) return null;
        for (PrioridadeEnum p : PrioridadeEnum.values()) {
            if (p.getNivel() == dbData) {
                return p;
            }
        }
        throw new IllegalArgumentException("Valor inválido para PrioridadeEnum: " + dbData);
    }
}
