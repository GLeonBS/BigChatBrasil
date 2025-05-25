package com.bigchatbrasil.modules.mensagem.dto;

import com.bigchatbrasil.modules.mensagem.enums.Prioridade;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateMensagemRequestDTO(UUID chatId, UUID destinatarioId, String texto, Prioridade prioridade,
                                       boolean whatsapp) {
}
