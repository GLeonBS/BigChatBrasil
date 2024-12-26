package com.bigchatbrasil.modules.destinatario.useCases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bigchatbrasil.modules.destinatario.dto.CreateDestinatarioRequestDTO;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.destinatario.repository.DestinatarioRepository;

@ExtendWith(MockitoExtension.class)
class CreateDestinatarioUseCaseTest {
    @Mock
    private DestinatarioRepository repository;

    @InjectMocks
    private CreateDestinatarioUseCase createDestinatarioUseCase;

    @Test
    void shouldBeCreateDestinatario() {
        var destinatario = CreateDestinatarioRequestDTO.builder()
                .nome("Nome")
                .numeroTelefone("44999999999")
                .build();

        DestinatarioEntity destinatarioEntity = new DestinatarioEntity();
        destinatarioEntity.setId(UUID.randomUUID());
        destinatarioEntity.setNome(destinatario.nome());
        destinatarioEntity.setNumeroTelefone(destinatario.numeroTelefone());
        when(repository.save(any(DestinatarioEntity.class))).thenReturn(destinatarioEntity);

        Assertions.assertDoesNotThrow(() -> createDestinatarioUseCase.execute(destinatario));
    }

}