package com.bigchatbrasil.modules.chat.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateChatRequestDTO(UUID remetente, UUID destinatario) {
}
