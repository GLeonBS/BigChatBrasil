package com.bigchatbrasil.modules.cliente.dto;

import lombok.Builder;

@Builder
public record CreateClienteRequestDTO(String nome, String email, String telefone, String cpfResponsavel, String cnpj,
                                      String nomeEmpresa, ContaRequestDTO conta) {
}
