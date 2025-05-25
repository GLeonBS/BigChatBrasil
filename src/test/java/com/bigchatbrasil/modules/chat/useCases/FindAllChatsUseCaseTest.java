package com.bigchatbrasil.modules.chat.useCases;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.modules.chat.dto.ChatResponseDTO;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
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
class FindAllChatsUseCaseTest {
    @Mock
    private ChatRepository chatRepository;

    @InjectMocks
    private FindAllChatsUseCase findAllChatsUseCase;

    @Test
    void testFindAllChats() {
        UUID clienteId = UUID.randomUUID();
        UUID destinatarioId = UUID.randomUUID();
        UUID chatId = UUID.randomUUID();
        var cliente = Fixtures.createCliente(clienteId);
        var destinatario = Fixtures.createDestinatario(destinatarioId, cliente);
        var chat = Fixtures.createChat(chatId, cliente, destinatario);
        var mensagem = Fixtures.createMensagem(UUID.randomUUID(), chat, "Mensagem de teste");
        chat.getMensagens().add(mensagem);

        when(chatRepository.findAllByRemetenteId(clienteId))
                .thenReturn(List.of(chat));
        List<ChatResponseDTO> result = findAllChatsUseCase.execute(clienteId);

        assertThat(result).isNotNull();
        assertThat(result).extracting(ChatResponseDTO::id).containsOnly(chatId);
        assertThat(result).extracting(ChatResponseDTO::clienteId).containsOnly(clienteId);
        assertThat(result).extracting(ChatResponseDTO::destinatarioId).containsOnly(destinatarioId);
        assertThat(result).extracting(ChatResponseDTO::nomeDestinatario).containsOnly(destinatario.getNome());
        assertThat(result).extracting(ChatResponseDTO::ultimaMensagem).containsOnly(mensagem.getTexto());
        assertThat(result).extracting(ChatResponseDTO::mensagensNaoLidas).containsOnly(1);
    }
}