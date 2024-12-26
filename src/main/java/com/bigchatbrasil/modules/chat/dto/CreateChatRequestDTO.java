package com.bigchatbrasil.modules.chat.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;

@Builder
public record CreateChatRequestDTO(UUID remetente, List<UUID> destinatarios) {
}
