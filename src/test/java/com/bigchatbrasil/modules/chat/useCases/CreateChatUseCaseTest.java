package com.bigchatbrasil.modules.chat.useCases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bigchatbrasil.modules.chat.dto.CreateChatRequestDTO;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.useCases.FindClienteUseCase;

@ExtendWith(MockitoExtension.class)
class CreateChatUseCaseTest {

    @Mock
    private ChatRepository repository;

    @Mock
    private FindClienteUseCase findClienteUseCase;

    @InjectMocks
    private CreateChatUseCase createChatUseCase;

    @Test
    void shouldBeCreateDestinatario() {
        UUID clienteId = UUID.randomUUID();
        var chat = CreateChatRequestDTO.builder()
                .remetente(clienteId)
                .build();
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(clienteId);

        when(findClienteUseCase.execute(clienteId)).thenReturn(clienteEntity);

        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId(UUID.randomUUID());
        chatEntity.setRemetente(clienteEntity);

        when(repository.save(any(ChatEntity.class))).thenReturn(chatEntity);

        Assertions.assertDoesNotThrow(() -> createChatUseCase.execute(chat));
    }

}