package com.bigchatbrasil.modules.cliente.dto;

import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.TipoDocumentoEnum;
import com.bigchatbrasil.modules.cliente.vo.Conta;

import java.util.UUID;

public record ClienteResponseDTO(UUID id, String nome, String documento, TipoDocumentoEnum tipoDocumento,
                                 ContaRequestDTO conta, boolean ativo) {
    public static ClienteResponseDTO from(ClienteEntity cliente) {
        Conta conta = cliente.getConta();
        ContaRequestDTO contaRequestDTO = ContaRequestDTO.builder()
                .plano(conta.getPlano())
                .credito(conta.getCredito())
                .limite(conta.getLimite())
                .limiteConsumido(conta.getLimiteConsumido())
                .build();
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getDocumento(),
                cliente.getTipoDocumento(),
                contaRequestDTO,
                cliente.getAtivo());

    }

}
