package com.bigchatbrasil.modules.mensagem.useCases;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.useCases.CheckSaldoPosPagoUseCase;
import com.bigchatbrasil.modules.cliente.useCases.CheckSaldoPrePagoUseCase;
import com.bigchatbrasil.modules.cliente.useCases.FindClienteUseCase;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.destinatario.repository.DestinatarioRepository;
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

import static org.mockito.ArgumentMatchers.any;
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

    @Mock
    private DestinatarioRepository destinatarioRepository;

    @InjectMocks
    private EnviarMensagensUseCase enviarMensagensUseCase;

    @BeforeEach
    void setUp() {
        enviarMensagensUseCase = new EnviarMensagensUseCase(repository, chatRepository,
                List.of(checkSaldoPosPagoUseCase, checkSaldoPrePagoUseCase),
                List.of(enviarMensagemWhatsappUseCase, enviarMensagemSMSUseCase), findClienteUseCase,
                destinatarioRepository);
    }

    @Test
    void shouldBeCreateMensagem() {
        UUID clientId = UUID.randomUUID();
        ClienteEntity cliente = Fixtures.createCliente(clientId);

        UUID destinatarioId = UUID.randomUUID();
        DestinatarioEntity destinatario = Fixtures.createDestinatario(destinatarioId, cliente);

        UUID chatId = UUID.randomUUID();
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId(chatId);
        chatEntity.setRemetente(cliente);

        var mensagem = CreateMensagemRequestDTO.builder()
                .texto("Olá!")
                .destinatarioId(destinatarioId)
                .whatsapp(false)
                .chatId(chatId)
                .build();

        MensagemEntity mensagemEntity = new MensagemEntity();
        mensagemEntity.setId(UUID.randomUUID());
        mensagemEntity.setTexto(mensagem.texto());
        mensagemEntity.setWhatsapp(mensagem.whatsapp());
        mensagemEntity.setChat(chatEntity);
        mensagemEntity.setCliente(cliente);
        mensagemEntity.setDestinatario(destinatario);

        when(checkSaldoPrePagoUseCase.getPlano()).thenReturn(PlanoEnum.PRE_PAGO);
        when(findClienteUseCase.execute(clientId)).thenReturn(cliente);
        when(chatRepository.findById(chatId)).thenReturn(java.util.Optional.of(chatEntity));
        when(destinatarioRepository.findById(destinatarioId)).thenReturn(java.util.Optional.of(destinatario));
        when(repository.save(any())).thenReturn(mensagemEntity);

        Assertions.assertDoesNotThrow(() -> enviarMensagensUseCase.execute(mensagem, clientId));
    }
}