package com.bigchatbrasil.modules.mensagem.dto;

import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.enums.Prioridade;
import com.bigchatbrasil.modules.mensagem.enums.StatusMensagem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record MensagemResponseDTO(
        UUID id,
        UUID clienteId,
        UUID destinatarioId,
        String texto,
        LocalDateTime dataHoraEnvio,
        Prioridade prioridade,
        StatusMensagem status,
        BigDecimal custo
) {
    public static MensagemResponseDTO from(MensagemEntity mensagem) {
        return new MensagemResponseDTO(
                mensagem.getId(),
                mensagem.getCliente().getId(),
                mensagem.getDestinatario().getId(),
                mensagem.getTexto(),
                mensagem.getDataHoraEnvio(),
                mensagem.getPrioridade(),
                mensagem.getStatus(),
                mensagem.getCusto()
        );
    }
}
