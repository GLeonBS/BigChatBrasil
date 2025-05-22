package com.bigchatbrasil.modules.cliente.dto;

import com.bigchatbrasil.modules.cliente.enums.TipoDocumento;
import lombok.Builder;

@Builder
public record CreateClienteRequestDTO(String nome, String documento, TipoDocumento tipoDocumento,
                                      ContaRequestDTO conta, boolean ativo) {
}
