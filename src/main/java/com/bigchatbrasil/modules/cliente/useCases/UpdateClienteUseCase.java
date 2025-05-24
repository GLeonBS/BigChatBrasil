package com.bigchatbrasil.modules.cliente.useCases;

import com.bigchatbrasil.modules.cliente.dto.ClienteRequestDTO;
import com.bigchatbrasil.modules.cliente.dto.ClienteResponseDTO;
import com.bigchatbrasil.modules.cliente.dto.ContaRequestDTO;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.cliente.vo.Conta;
import com.bigchatbrasil.utils.MyBeansUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UpdateClienteUseCase {

    private final ClienteRepository repository;
    private final FindClienteUseCase findClienteUseCase;

    public ClienteResponseDTO execute(UUID id, ClienteRequestDTO updateClienteRequestDTO) {
        ClienteEntity cliente = findClienteUseCase.execute(id);

        Conta conta = new Conta();

        MyBeansUtil<Object, Object> myBeansUtil = new MyBeansUtil<>();
        myBeansUtil.copyNonNullProperties(cliente, updateClienteRequestDTO);
        myBeansUtil.copyNonNullProperties(conta, updateClienteRequestDTO.conta());
        cliente.setId(id);
        cliente.setConta(conta);

        ClienteEntity clienteSalvo = repository.save(cliente);
        Conta contaSalva = clienteSalvo.getConta();
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();
        ContaRequestDTO contaRequestDTO = ContaRequestDTO.builder()
                .plano(contaSalva.getPlano())
                .limite(contaSalva.getLimite())
                .limiteConsumido(contaSalva.getLimiteConsumido())
                .build();

        BeanUtils.copyProperties(clienteSalvo, clienteResponseDTO);
        clienteResponseDTO.setConta(contaRequestDTO);

        return clienteResponseDTO;
    }

}
