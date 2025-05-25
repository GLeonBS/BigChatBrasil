package com.bigchatbrasil.modules.destinatario.useCases;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.modules.cliente.useCases.FindClienteUseCase;
import com.bigchatbrasil.modules.destinatario.dto.CreateDestinatarioRequestDTO;
import com.bigchatbrasil.modules.destinatario.dto.DestinatarioRespondeDTO;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.destinatario.repository.DestinatarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateDestinatarioUseCaseTest {
    @Mock
    private DestinatarioRepository repository;

    @Mock
    private FindClienteUseCase findClienteUseCase;

    @InjectMocks
    private CreateDestinatarioUseCase createDestinatarioUseCase;

    @Test
    void shouldBeCreateDestinatario() {
        UUID clienteId = UUID.randomUUID();
        var cliente = Fixtures.createCliente(clienteId);

        var destinatario = CreateDestinatarioRequestDTO.builder()
                .nome("Nome")
                .numeroTelefone("44999999999")
                .build();

        DestinatarioEntity destinatarioEntity = new DestinatarioEntity();
        destinatarioEntity.setId(UUID.randomUUID());
        destinatarioEntity.setNome(destinatario.nome());
        destinatarioEntity.setNumeroTelefone(destinatario.numeroTelefone());
        destinatarioEntity.setCliente(cliente);

        when(repository.save(any(DestinatarioEntity.class))).thenReturn(destinatarioEntity);

        Assertions.assertDoesNotThrow(() -> {
            DestinatarioRespondeDTO destinatarioRespondeDTO = createDestinatarioUseCase.execute(destinatario);
            Assertions.assertNotNull(destinatarioRespondeDTO);
            assertThat(destinatarioRespondeDTO.id()).isEqualTo(destinatarioEntity.getId());
            assertThat(destinatarioRespondeDTO.nome()).isEqualTo(destinatarioEntity.getNome());
            assertThat(destinatarioRespondeDTO.numeroTelefone()).isEqualTo(destinatarioEntity.getNumeroTelefone());
            assertThat(destinatarioRespondeDTO.clienteId()).isEqualTo(clienteId);
        });
    }

}