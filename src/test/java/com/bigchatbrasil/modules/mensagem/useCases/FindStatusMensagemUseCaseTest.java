package com.bigchatbrasil.modules.mensagem.useCases;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.enums.StatusMensagem;
import com.bigchatbrasil.modules.mensagem.repository.MensagemRepository;
import org.junit.jupiter.api.Assertions;
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
class FindStatusMensagemUseCaseTest {

    @Mock
    private MensagemRepository repository;

    @InjectMocks
    private FindStatusMensagemUseCase findStatusMensagemUseCase;

    @Test
    void shouldBeFindOneMensagem() {
        UUID clientId = UUID.randomUUID();
        ClienteEntity cliente = Fixtures.createCliente(clientId);

        UUID destinatarioId = UUID.randomUUID();
        DestinatarioEntity destinatario = Fixtures.createDestinatario(destinatarioId, cliente);

        UUID chatId = UUID.randomUUID();
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId(chatId);
        chatEntity.setRemetente(cliente);

        MensagemEntity mensagemEntity = new MensagemEntity();
        mensagemEntity.setId(UUID.randomUUID());
        mensagemEntity.setTexto("Teste mensagem");
        mensagemEntity.setWhatsapp(true);
        mensagemEntity.setChat(chatEntity);
        mensagemEntity.setCliente(cliente);
        mensagemEntity.setDestinatario(destinatario);
        mensagemEntity.setStatus(StatusMensagem.ENTREGUE);

        when(repository.findById(mensagemEntity.getId())).thenReturn(Optional.of(mensagemEntity));

        Assertions.assertDoesNotThrow(() -> {
            StatusMensagem mensagemResponse = findStatusMensagemUseCase.execute(mensagemEntity.getId());
            Assertions.assertNotNull(mensagemResponse);
            assertThat(mensagemResponse).isEqualTo(mensagemEntity.getStatus());
        });
    }

}