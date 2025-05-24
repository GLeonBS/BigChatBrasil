package com.bigchatbrasil.modules.cliente.useCases;

import com.bigchatbrasil.exceptions.UserNotFoundException;
import com.bigchatbrasil.modules.cliente.dto.ClienteResponseDTO;
import com.bigchatbrasil.modules.cliente.dto.ContaRequestDTO;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.cliente.vo.Conta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FindClienteUseCase {

    private final ClienteRepository repository;

    public ClienteEntity execute(UUID id) {
        return this.repository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public ClienteResponseDTO executeResponse(UUID id) {
        ClienteEntity clienteEncontrado = this.execute(id);
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();
        clienteResponseDTO.setId(clienteEncontrado.getId());
        clienteResponseDTO.setNome(clienteEncontrado.getNome());
        clienteResponseDTO.setDocumento(clienteEncontrado.getDocumento());

        Conta conta = clienteEncontrado.getConta();
        ContaRequestDTO contaRequestDTO = ContaRequestDTO.builder()
                .plano(conta.getPlano())
                .limite(conta.getLimite())
                .limiteConsumido(conta.getLimiteConsumido())
                .build();
        clienteResponseDTO.setConta(contaRequestDTO);

        return clienteResponseDTO;

    }

}
