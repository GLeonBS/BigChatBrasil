package com.bigchatbrasil.modules.cliente.dto;

import com.bigchatbrasil.modules.cliente.enums.TipoDocumento;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UpdateClienteRequestDTO(UUID id, String nome, String telefone, String documento,
                                      TipoDocumento tipoDocumento, ContaRequestDTO conta, boolean ativo) {
}
