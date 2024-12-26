package com.bigchatbrasil.modules.chat.useCases;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.bigchatbrasil.modules.chat.dto.CreateChatRequestDTO;
import com.bigchatbrasil.modules.chat.entity.ChatDestinatarioEntity;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.chat.repository.ChatDestinatarioRepository;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.useCases.FindClienteUseCase;
import com.bigchatbrasil.modules.destinatario.repository.DestinatarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateChatUseCase {

    private final ChatRepository repository;

    private final FindClienteUseCase findClienteUseCase;

    private final DestinatarioRepository destinatarioRepository;

    private final ChatDestinatarioRepository chatDestinatarioRepository;

    public ChatEntity execute(CreateChatRequestDTO chatRequestDTO) {
        ChatEntity chatEntity = new ChatEntity();
        ClienteEntity cliente = findClienteUseCase.execute(chatRequestDTO.remetente());
        chatEntity.setRemetente(cliente);

        ChatEntity chatSalvo = repository.save(chatEntity);

        if (Objects.nonNull(chatRequestDTO.destinatarios())) {
            chatRequestDTO.destinatarios().forEach(destinatario -> {
                destinatarioRepository.findById(destinatario).ifPresent(dest -> {
                    ChatDestinatarioEntity chatDestinatario = new ChatDestinatarioEntity();
                    chatDestinatario.setChat(chatSalvo);
                    chatDestinatario.setDestinatario(dest);
                    chatSalvo.getDestinatarios().add(chatDestinatario);
                    dest.getChats().add(chatDestinatario);

                    chatDestinatarioRepository.save(chatDestinatario);
                });
            });
        }

        return chatSalvo;
    }

}
