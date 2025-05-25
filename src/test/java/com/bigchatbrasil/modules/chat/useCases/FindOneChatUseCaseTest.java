package com.bigchatbrasil.modules.chat.useCases;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.modules.chat.dto.ChatResponseDTO;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindOneChatUseCaseTest {
    @Mock
    private ChatRepository chatRepository;

    @InjectMocks
    private FindOneChatUseCase findOneChatUseCase;

    @Test
    void testFindOneChat() {
        UUID clienteId = UUID.randomUUID();
        UUID destinatarioId = UUID.randomUUID();
        UUID chatId = UUID.randomUUID();
        var cliente = Fixtures.createCliente(clienteId);
        var destinatario = Fixtures.createDestinatario(destinatarioId, cliente);
        var chat = Fixtures.createChat(chatId, cliente, destinatario);
        var mensagem = Fixtures.createMensagem(UUID.randomUUID(), chat, "Mensagem de teste");
        chat.getMensagens().add(mensagem);

        when(chatRepository.findById(chatId))
                .thenReturn(Optional.of(chat));
        ChatResponseDTO result = findOneChatUseCase.execute(chatId);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(chatId);
        assertThat(result.clienteId()).isEqualTo(clienteId);
        assertThat(result.destinatarioId()).isEqualTo(destinatarioId);
        assertThat(result.nomeDestinatario()).isEqualTo(destinatario.getNome());
        assertThat(result.ultimaMensagem()).isEqualTo(mensagem.getTexto());
        assertThat(result.mensagensNaoLidas()).isEqualTo(1);
    }
}