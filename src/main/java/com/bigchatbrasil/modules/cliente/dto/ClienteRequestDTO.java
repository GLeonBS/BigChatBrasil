package com.bigchatbrasil.modules.cliente.dto;

import com.bigchatbrasil.modules.cliente.enums.RoleEnum;
import com.bigchatbrasil.modules.cliente.enums.TipoDocumentoEnum;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ClienteRequestDTO(UUID id, String nome, String documento, TipoDocumentoEnum tipoDocumento,
                                ContaRequestDTO conta, boolean ativo, String numeroTelefone, String senha,
                                RoleEnum role) {
}
