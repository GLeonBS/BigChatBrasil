package com.bigchatbrasil.modules.destinatario.dto;

import java.util.UUID;

public record DestinatarioRespondeDTO(UUID id, String nome, String numeroTelefone, UUID clienteId) {
}
