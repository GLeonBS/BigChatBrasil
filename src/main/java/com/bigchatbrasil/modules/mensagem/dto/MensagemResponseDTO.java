package com.bigchatbrasil.modules.mensagem.dto;

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
}
