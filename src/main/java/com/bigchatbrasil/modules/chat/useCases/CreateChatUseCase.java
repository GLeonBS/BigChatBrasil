package com.bigchatbrasil.modules.chat.useCases;

import com.bigchatbrasil.modules.chat.dto.CreateChatRequestDTO;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.useCases.FindClienteUseCase;
import com.bigchatbrasil.modules.destinatario.repository.DestinatarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateChatUseCase {

    private final ChatRepository repository;

    private final FindClienteUseCase findClienteUseCase;

    private final DestinatarioRepository destinatarioRepository;

    public ChatEntity execute(CreateChatRequestDTO chatRequestDTO) {
        ChatEntity chatEntity = new ChatEntity();
        ClienteEntity cliente = findClienteUseCase.execute(chatRequestDTO.remetente());
        chatEntity.setRemetente(cliente);

        ChatEntity chatSalvo = repository.save(chatEntity);

        return chatSalvo;
    }

}
