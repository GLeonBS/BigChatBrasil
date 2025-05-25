package com.bigchatbrasil.modules.chat.useCases;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.mensagem.dto.MensagemResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindMessagesChatUseCaseTest {
    @Mock
    private ChatRepository chatRepository;

    @InjectMocks
    private FindMessagesChatUseCase findMessagesChatUseCase;

    @Test
    void testFindMessagesChat() {
        UUID clienteId = UUID.randomUUID();
        UUID destinatarioId = UUID.randomUUID();
        UUID chatId = UUID.randomUUID();
        var cliente = Fixtures.createCliente(clienteId);
        var destinatario = Fixtures.createDestinatario(destinatarioId, cliente);
        var chat = Fixtures.createChat(chatId, cliente, destinatario);
        var mensagem = Fixtures.createMensagem(UUID.randomUUID(), chat, "Mensagem de teste");
        var mensagem2 = Fixtures.createMensagem(UUID.randomUUID(), chat, "Mensagem de teste2");
        chat.getMensagens().addAll(List.of(mensagem, mensagem2));

        when(chatRepository.findById(chatId))
                .thenReturn(Optional.of(chat));
        List<MensagemResponseDTO> result = findMessagesChatUseCase.execute(chatId);

        assertThat(result).isNotNull();
        assertThat(result).extracting(MensagemResponseDTO::id).containsExactlyInAnyOrder(mensagem.getId(), mensagem2.getId());
        assertThat(result).extracting(MensagemResponseDTO::texto).containsExactlyInAnyOrder(mensagem.getTexto(), mensagem2.getTexto());
        assertThat(result).extracting(MensagemResponseDTO::clienteId).containsOnly(clienteId);
        assertThat(result).extracting(MensagemResponseDTO::destinatarioId).containsOnly(destinatarioId);
        assertThat(result).extracting(MensagemResponseDTO::prioridade).containsExactlyInAnyOrder(mensagem.getPrioridade(), mensagem2.getPrioridade());
        assertThat(result).extracting(MensagemResponseDTO::status).containsExactlyInAnyOrder(mensagem.getStatus(), mensagem2.getStatus());
        assertThat(result).extracting(MensagemResponseDTO::custo).containsExactlyInAnyOrder(mensagem.getCusto(), mensagem2.getCusto());
    }
}