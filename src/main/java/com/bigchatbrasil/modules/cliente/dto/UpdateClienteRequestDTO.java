package com.bigchatbrasil.modules.cliente.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record UpdateClienteRequestDTO(UUID id, String nome, String email, String telefone, String cpfResponsavel,
                                      String cnpj, String nomeEmpresa, ContaRequestDTO conta) {
}
