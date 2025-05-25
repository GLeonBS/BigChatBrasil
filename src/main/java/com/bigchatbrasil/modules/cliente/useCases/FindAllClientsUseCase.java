package com.bigchatbrasil.modules.cliente.useCases;

import com.bigchatbrasil.modules.cliente.dto.ClienteResponseDTO;
import com.bigchatbrasil.modules.cliente.dto.ContaRequestDTO;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindAllClientsUseCase {

    private final ClienteRepository repository;

    public List<ClienteResponseDTO> execute() {
        List<ClienteEntity> clientes = this.repository.findAll();
        return clientes.stream()
                .map(cliente -> {
                    ContaRequestDTO contaRequestDTO = ContaRequestDTO.builder()
                            .plano(cliente.getConta().getPlano())
                            .credito(cliente.getConta().getCredito())
                            .limite(cliente.getConta().getLimite())
                            .limiteConsumido(cliente.getConta().getLimiteConsumido())
                            .build();
                    return ClienteResponseDTO.builder()
                            .id(cliente.getId())
                            .nome(cliente.getNome())
                            .documento(cliente.getDocumento())
                            .tipoDocumento(cliente.getTipoDocumento())
                            .conta(contaRequestDTO)
                            .ativo(cliente.getAtivo())
                            .build();
                })
                .toList();
    }
}
