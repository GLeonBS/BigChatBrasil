package com.bigchatbrasil.modules.cliente.useCases;

import com.bigchatbrasil.config.Fixtures;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteClienteUseCaseTest {
    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private DeleteClienteUseCase deleteClienteUseCase;


    @Test
    void shouldBeDeleteCliente() {
        UUID id = UUID.randomUUID();
        ClienteEntity cliente = Fixtures.createCliente(id);

        when(repository.findById(id)).thenReturn(Optional.of(cliente));


        Assertions.assertDoesNotThrow(() -> {
            deleteClienteUseCase.execute(id);
        });
    }
}