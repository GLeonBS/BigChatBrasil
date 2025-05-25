package com.bigchatbrasil.modules.chat.dto;

import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ChatResponseDTO(UUID id, UUID clienteId, UUID destinatarioId, String nomeDestinatario,
                              String ultimaMensagem, int mensagensNaoLidas) {
    public static ChatResponseDTO from(ChatEntity chatEntity, String ultimaMensagem, int mensagensNaoLidas) {
        return new ChatResponseDTO(
                chatEntity.getId(),
                chatEntity.getRemetente().getId(),
                chatEntity.getDestinatario().getId(),
                chatEntity.getDestinatario().getNome(),
                ultimaMensagem == null ? "" : ultimaMensagem,
                mensagensNaoLidas
        );
    }
}
