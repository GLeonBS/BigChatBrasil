package com.bigchatbrasil.modules.cliente.dto;

import com.bigchatbrasil.modules.cliente.enums.TipoDocumento;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ClienteRequestDTO(UUID id, String nome, String documento, TipoDocumento tipoDocumento,
                                ContaRequestDTO conta, boolean ativo, String numeroTelefone) {
}
