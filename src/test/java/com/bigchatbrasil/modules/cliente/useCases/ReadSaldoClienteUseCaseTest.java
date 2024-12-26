package com.bigchatbrasil.modules.cliente.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
class ReadSaldoClienteUseCaseTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ReadSaldoClienteUseCase readSaldoClienteUseCase;

    @Test
    void shouldBeFindClienteById() {
        ClienteEntity cliente = Fixtures.createCliente(UUID.randomUUID());
        when(repository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        Assertions.assertDoesNotThrow(() -> {
            readSaldoClienteUseCase.execute(cliente.getId());
        });
        assertThat(readSaldoClienteUseCase.execute(cliente.getId())).isEqualTo(new BigDecimal("100.00"));
    }

    @Test
    void shouldBeNotFindClienteById() {
        ClienteEntity cliente = Fixtures.createCliente(UUID.randomUUID());
        when(repository.findById(cliente.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            readSaldoClienteUseCase.execute(cliente.getId());
        }).isInstanceOf(UserNotFoundException.class);
    }
}