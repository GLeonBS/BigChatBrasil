package com.bigchatbrasil.modules.mensagem.useCases;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.mensagem.dto.MensagemResponseDTO;
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
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
class FindOneMensagemUseCaseTest {
    @Mock
    private MensagemRepository repository;

    @InjectMocks
    private FindOneMensagemUseCase findOneMensagemUseCase;

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

        when(repository.findById(mensagemEntity.getId())).thenReturn(Optional.of(mensagemEntity));

        Assertions.assertDoesNotThrow(() -> {
            MensagemResponseDTO mensagemResponse = findOneMensagemUseCase.execute(mensagemEntity.getId());
            Assertions.assertNotNull(mensagemResponse);
            assertThat(mensagemResponse.id()).isEqualTo(mensagemEntity.getId());
            assertThat(mensagemResponse.texto()).isEqualTo(mensagemEntity.getTexto());
            assertThat(mensagemResponse.clienteId()).isEqualTo(mensagemEntity.getCliente().getId());
            assertThat(mensagemResponse.destinatarioId()).isEqualTo(mensagemEntity.getDestinatario().getId());
            assertThat(mensagemResponse.dataHoraEnvio()).isEqualTo(mensagemEntity.getDataHoraEnvio());
            assertThat(mensagemResponse.prioridade()).isEqualTo(mensagemEntity.getPrioridade());
            assertThat(mensagemResponse.status()).isEqualTo(mensagemEntity.getStatus());
            assertThat(mensagemResponse.custo()).isEqualTo(mensagemEntity.getCusto());
        });
    }
}