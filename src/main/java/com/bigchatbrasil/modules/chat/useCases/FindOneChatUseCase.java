package com.bigchatbrasil.modules.chat.useCases;

import com.bigchatbrasil.exceptions.ChatNotFoundException;
import com.bigchatbrasil.modules.chat.dto.ChatResponseDTO;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.mensagem.enums.StatusMensagem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FindOneChatUseCase {
    private ChatRepository chatRepository;

    public ChatResponseDTO execute(UUID id) {
        ChatEntity chat = chatRepository.findById(id).orElseThrow(ChatNotFoundException::new);
        return ChatResponseDTO.from(chat,
                chat.getMensagens().isEmpty() ? "" : chat.getMensagens().get(chat.getMensagens().size() - 1).getTexto(),
                (int) chat.getMensagens().stream()
                        .filter(mensagem -> !StatusMensagem.LIDA.equals(mensagem.getStatus()))
                        .count());
    }


}
