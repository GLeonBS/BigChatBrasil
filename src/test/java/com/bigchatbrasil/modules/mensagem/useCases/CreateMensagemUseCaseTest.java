package com.bigchatbrasil.modules.mensagem.useCases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.mensagem.dto.CreateMensagemRequestDTO;
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.repository.MensagemRepository;

@ExtendWith(MockitoExtension.class)
class CreateMensagemUseCaseTest {
    @Mock
    private MensagemRepository repository;

    @Mock
    private ChatRepository chatRepository;

    @InjectMocks
    private CreateMensagemUseCase createMensagemUseCase;

    @Test
    void shouldBeCreateDestinatario() {
        UUID chatId = UUID.randomUUID();
        var mensagem = CreateMensagemRequestDTO.builder()
                .texto("Olá!")
                .whatsapp(false)
                .numeroTelefone("44999999999")
                .chatId(chatId)
                .build();

        MensagemEntity mensagemEntity = new MensagemEntity();
        mensagemEntity.setId(UUID.randomUUID());
        mensagemEntity.setTexto(mensagem.texto());
        mensagemEntity.setWhatsapp(mensagem.whatsapp());
        mensagemEntity.setNumeroTelefone(mensagem.numeroTelefone());

        when(repository.save(any(MensagemEntity.class))).thenReturn(mensagemEntity);

        Assertions.assertDoesNotThrow(() -> createMensagemUseCase.execute(mensagem));
    }
}