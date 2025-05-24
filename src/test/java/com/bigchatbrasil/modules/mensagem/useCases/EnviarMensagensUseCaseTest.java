package com.bigchatbrasil.modules.mensagem.useCases;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.useCases.CheckSaldoPosPagoUseCase;
import com.bigchatbrasil.modules.cliente.useCases.CheckSaldoPrePagoUseCase;
import com.bigchatbrasil.modules.cliente.useCases.FindClienteUseCase;
import com.bigchatbrasil.modules.mensagem.dto.CreateMensagemRequestDTO;
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.repository.MensagemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnviarMensagensUseCaseTest {
    @Mock
    private MensagemRepository repository;

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private CheckSaldoPosPagoUseCase checkSaldoPosPagoUseCase;

    @Mock
    private CheckSaldoPrePagoUseCase checkSaldoPrePagoUseCase;

    @Mock
    private EnviarMensagemWhatsappUseCase enviarMensagemWhatsappUseCase;

    @Mock
    private EnviarMensagemSMSUseCase enviarMensagemSMSUseCase;

    @Mock
    private FindClienteUseCase findClienteUseCase;

    @InjectMocks
    private EnviarMensagensUseCase enviarMensagensUseCase;

    @BeforeEach
    void setUp() {
        enviarMensagensUseCase = new EnviarMensagensUseCase(repository, chatRepository,
                List.of(checkSaldoPosPagoUseCase, checkSaldoPrePagoUseCase),
                List.of(enviarMensagemWhatsappUseCase, enviarMensagemSMSUseCase), findClienteUseCase);
    }

    @Test
    void shouldBeCreateMensagem() {
        UUID clientId = UUID.randomUUID();
        ClienteEntity cliente = Fixtures.createCliente(clientId);

        UUID chatId = UUID.randomUUID();
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId(chatId);
        chatEntity.setRemetente(cliente);

        var mensagem = CreateMensagemRequestDTO.builder()
                .texto("Olá!")
                .clienteId(clientId)
                .whatsapp(false)
                .chatId(chatId)
                .build();

        MensagemEntity mensagemEntity = new MensagemEntity();
        mensagemEntity.setId(UUID.randomUUID());
        mensagemEntity.setTexto(mensagem.texto());
        mensagemEntity.setWhatsapp(mensagem.whatsapp());

        List<ChatEntity> chats = List.of(chatEntity);

        when(chatRepository.findAllByIdIn(List.of(chatId))).thenReturn(chats);
        when(checkSaldoPrePagoUseCase.getPlano()).thenReturn(PlanoEnum.PRE_PAGO);
        when(findClienteUseCase.execute(clientId)).thenReturn(cliente);

        Assertions.assertDoesNotThrow(() -> enviarMensagensUseCase.execute(List.of(mensagem), clientId));
    }
}