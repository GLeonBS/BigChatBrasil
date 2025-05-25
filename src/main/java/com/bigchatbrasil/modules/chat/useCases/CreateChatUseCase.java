package com.bigchatbrasil.modules.chat.useCases;

import com.bigchatbrasil.exceptions.DestinatarioNotFoundException;
import com.bigchatbrasil.modules.chat.dto.ChatResponseDTO;
import com.bigchatbrasil.modules.chat.dto.CreateChatRequestDTO;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.useCases.FindClienteUseCase;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.destinatario.repository.DestinatarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateChatUseCase {

    private final ChatRepository repository;

    private final FindClienteUseCase findClienteUseCase;

    private final DestinatarioRepository destinatarioRepository;

    public ChatResponseDTO execute(CreateChatRequestDTO chatRequestDTO) {
        ClienteEntity cliente = findClienteUseCase.execute(chatRequestDTO.remetente());
        DestinatarioEntity destinatario = destinatarioRepository.findById(chatRequestDTO.destinatario())
                .orElseThrow(DestinatarioNotFoundException::new);
        ChatEntity chatEntity = ChatEntity.builder()
                .remetente(cliente)
                .destinatario(destinatario)
                .build();
        ChatEntity chatSalvo = repository.save(chatEntity);

        return ChatResponseDTO.from(chatSalvo, null, 0);
    }

}
