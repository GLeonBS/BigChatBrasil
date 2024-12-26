package com.bigchatbrasil.modules.mensagem.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record CreateMensagemRequestDTO(String numeroTelefone, boolean whatsapp, String texto, UUID chatId) {
}
