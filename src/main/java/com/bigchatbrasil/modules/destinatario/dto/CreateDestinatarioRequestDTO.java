package com.bigchatbrasil.modules.destinatario.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record CreateDestinatarioRequestDTO(String nome, String numeroTelefone, UUID clienteId) {
}
