package com.bigchatbrasil.modules.cliente.useCases;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.modules.cliente.dto.ContaRequestDTO;
import com.bigchatbrasil.modules.cliente.dto.UpdateClienteRequestDTO;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
class UpdateClienteUseCaseTest {
    @Mock
    private ClienteRepository repository;

    @Mock
    private FindClienteUseCase findClienteUseCase;

    @InjectMocks
    private UpdateClienteUseCase updateClienteUseCase;

    @Test
    void shouldBeFindClienteById() {
        UUID id = UUID.randomUUID();

        ContaRequestDTO contaRequestDTO = ContaRequestDTO.builder()
                .plano(PlanoEnum.POS_PAGO)
                .limite(new BigDecimal("200.00"))
                .build();

        UpdateClienteRequestDTO updateClienteRequestDTO = UpdateClienteRequestDTO.builder()
                .id(id)
                .conta(contaRequestDTO)
                .build();

        ClienteEntity cliente = Fixtures.createCliente(id);
        when(findClienteUseCase.execute(cliente.getId())).thenReturn(cliente);

        Assertions.assertDoesNotThrow(() -> {
            updateClienteUseCase.execute(updateClienteRequestDTO);
        });
    }

}