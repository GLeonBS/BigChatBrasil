package com.bigchatbrasil.modules.cliente.useCases;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.exceptions.UserNotFoundException;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;

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

}