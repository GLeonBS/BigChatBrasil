package com.bigchatbrasil.modules.cliente.useCases;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.exceptions.UserNotFoundException;
import com.bigchatbrasil.modules.cliente.dto.ClienteResponseDTO;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindClienteUseCaseTest {
    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private FindClienteUseCase findClienteUseCase;

    @Test
    void shouldBeFindClienteById() {
        ClienteEntity cliente = Fixtures.createCliente(UUID.randomUUID());
        when(repository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        Assertions.assertDoesNotThrow(() -> {
            findClienteUseCase.execute(cliente.getId());
        });
    }

    @Test
    void shouldBeNotFindClienteById() {
        ClienteEntity cliente = Fixtures.createCliente(UUID.randomUUID());
        when(repository.findById(cliente.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            findClienteUseCase.execute(cliente.getId());
        }).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldBeCreateContaRequest() {
        ClienteEntity cliente = Fixtures.createCliente(UUID.randomUUID());
        when(repository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        Assertions.assertDoesNotThrow(() -> {
            ClienteResponseDTO clienteResponseDTO = findClienteUseCase.executeResponse(cliente.getId());
            Assertions.assertEquals(cliente.getId(), clienteResponseDTO.getId());
        });
    }

}