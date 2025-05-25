package com.bigchatbrasil.modules.chat.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ChatResponseDTO(UUID id, UUID clienteId, UUID destinatarioId, String nomeDestinatario,
                              String ultimaMensagem, int mensagensNaoLidas) {
}
