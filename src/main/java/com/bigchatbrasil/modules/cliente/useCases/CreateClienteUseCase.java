package com.bigchatbrasil.modules.cliente.useCases;

import com.bigchatbrasil.exceptions.UserFoundException;
import com.bigchatbrasil.modules.cliente.dto.ClienteRequestDTO;
import com.bigchatbrasil.modules.cliente.dto.ClienteResponseDTO;
import com.bigchatbrasil.modules.cliente.dto.ContaRequestDTO;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.Role;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.cliente.vo.Conta;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CreateClienteUseCase {

    private final ClienteRepository repository;

    private PasswordEncoder passwordEncoder;

    public ClienteResponseDTO execute(ClienteRequestDTO clienteRequestDTO) {
        repository.findByDocumento(clienteRequestDTO.documento())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        Conta conta = new Conta();
        ClienteEntity clienteEntity = new ClienteEntity();

        BeanUtils.copyProperties(clienteRequestDTO, clienteEntity);
        BeanUtils.copyProperties(clienteRequestDTO.conta(), conta);
        clienteEntity.setConta(conta);
        clienteEntity.getConta().setLimiteConsumido(BigDecimal.ZERO);
        clienteEntity.setRole(Role.ROLE_CLIENTE);
        clienteEntity.setSenha(passwordEncoder.encode(clienteRequestDTO.senha()));

        ClienteEntity clienteCriado = repository.save(clienteEntity);
        Conta contaCriada = clienteCriado.getConta();

        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();
        ContaRequestDTO contaRequestDTO = ContaRequestDTO.builder()
                .plano(contaCriada.getPlano())
                .limite(contaCriada.getLimite())
                .limiteConsumido(contaCriada.getLimiteConsumido())
                .build();

        BeanUtils.copyProperties(clienteCriado, clienteResponseDTO);
        clienteResponseDTO.setConta(contaRequestDTO);

        return clienteResponseDTO;
    }

}
