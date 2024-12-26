package com.bigchatbrasil.modules.cliente.useCases;

import org.springframework.stereotype.Service;

import com.bigchatbrasil.modules.cliente.dto.UpdateClienteRequestDTO;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.cliente.vo.Conta;
import com.bigchatbrasil.utils.MyBeansUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UpdateClienteUseCase {

    private final ClienteRepository repository;
    private final FindClienteUseCase findClienteUseCase;

    public ClienteEntity execute(UpdateClienteRequestDTO updateClienteRequestDTO) {
        ClienteEntity cliente = findClienteUseCase.execute(updateClienteRequestDTO.id());

        Conta conta = new Conta();

        MyBeansUtil<Object, Object> myBeansUtil = new MyBeansUtil<>();
        myBeansUtil.copyNonNullProperties(cliente, updateClienteRequestDTO);
        myBeansUtil.copyNonNullProperties(conta, updateClienteRequestDTO.conta());

        cliente.setConta(conta);

        return repository.save(cliente);
    }

}
