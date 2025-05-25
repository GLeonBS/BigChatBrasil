package com.bigchatbrasil.modules.chat.useCases;

import com.bigchatbrasil.exceptions.ChatNotFoundException;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.mensagem.dto.MensagemResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FindMessagesChatUseCase {
    private ChatRepository chatRepository;

    public List<MensagemResponseDTO> execute(UUID id) {
        ChatEntity chats = chatRepository.findById(id).orElseThrow(ChatNotFoundException::new);
        
        return chats.getMensagens().stream()
                .map(MensagemResponseDTO::from)
                .toList();
    }


}
