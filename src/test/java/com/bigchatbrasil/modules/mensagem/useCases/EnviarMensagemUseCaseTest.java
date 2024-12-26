package com.bigchatbrasil.modules.mensagem.useCases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.useCases.CheckSaldoPosPagoUseCase;
import com.bigchatbrasil.modules.cliente.useCases.CheckSaldoPrePagoUseCase;
import com.bigchatbrasil.modules.mensagem.dto.CreateMensagemRequestDTO;
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.repository.MensagemRepository;

@ExtendWith(MockitoExtension.class)
class EnviarMensagemUseCaseTest {
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

    @InjectMocks
    private EnviarMensagemUseCase enviarMensagemUseCase;

    @BeforeEach
    void setUp() {
        enviarMensagemUseCase = new EnviarMensagemUseCase(repository, chatRepository,
                List.of(checkSaldoPosPagoUseCase, checkSaldoPrePagoUseCase),
                List.of(enviarMensagemWhatsappUseCase, enviarMensagemSMSUseCase));
    }

    @Test
    void shouldBeCreateMensagem() {
        ClienteEntity cliente = Fixtures.createCliente(UUID.randomUUID());

        UUID chatId = UUID.randomUUID();
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId(chatId);
        chatEntity.setRemetente(cliente);

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
        when(chatRepository.findById(chatId)).thenReturn(java.util.Optional.of(chatEntity));
        when(checkSaldoPrePagoUseCase.getPlano()).thenReturn(PlanoEnum.PRE_PAGO);

        Assertions.assertDoesNotThrow(() -> enviarMensagemUseCase.execute(mensagem));
    }
}