package com.bigchatbrasil.modules.chat.useCases;

import com.bigchatbrasil.modules.chat.dto.CreateChatRequestDTO;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.useCases.FindClienteUseCase;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.destinatario.repository.DestinatarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateChatUseCaseTest {

    @Mock
    private ChatRepository repository;

    @Mock
    private FindClienteUseCase findClienteUseCase;

    @Mock
    private DestinatarioRepository destinatarioRepository;

    @InjectMocks
    private CreateChatUseCase createChatUseCase;

    @Test
    void shouldBeCreateDestinatario() {
        UUID clienteId = UUID.randomUUID();
        UUID destinatarioId = UUID.randomUUID();
        var chat = CreateChatRequestDTO.builder()
                .remetente(clienteId)
                .destinatario(destinatarioId)
                .build();

        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(clienteId);

        DestinatarioEntity destinatarioEntity = new DestinatarioEntity();
        destinatarioEntity.setId(destinatarioId);

        when(findClienteUseCase.execute(clienteId)).thenReturn(clienteEntity);
        when(destinatarioRepository.findById(destinatarioId)).thenReturn(Optional.of(destinatarioEntity));

        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId(UUID.randomUUID());
        chatEntity.setRemetente(clienteEntity);
        chatEntity.setDestinatario(destinatarioEntity);

        when(repository.save(any(ChatEntity.class))).thenReturn(chatEntity);

        Assertions.assertDoesNotThrow(() -> createChatUseCase.execute(chat));
    }

}