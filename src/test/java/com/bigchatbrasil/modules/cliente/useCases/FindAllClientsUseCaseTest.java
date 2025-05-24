package com.bigchatbrasil.modules.cliente.useCases;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.modules.cliente.dto.ClienteResponseDTO;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllClientsUseCaseTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private FindAllClientsUseCase findClienteUseCase;

    @Test
    void shouldBeFindAllClients() {
        ClienteEntity cliente = Fixtures.createCliente(UUID.randomUUID());
        ClienteEntity cliente2 = Fixtures.createCliente(UUID.randomUUID());
        cliente2.setNome("Augusto");

        when(repository.findAll()).thenReturn(List.of(cliente, cliente2));

        Assertions.assertDoesNotThrow(() -> {
            List<ClienteResponseDTO> clientes = findClienteUseCase.execute();
            Assertions.assertEquals(2, clientes.size());
            assertThat(clientes).extracting(ClienteResponseDTO::getNome).containsAll(List.of(cliente.getNome(), cliente2.getNome()));
        });
    }

}