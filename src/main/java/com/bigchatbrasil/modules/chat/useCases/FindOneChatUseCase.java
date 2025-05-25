package com.bigchatbrasil.modules.chat.useCases;

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
        ChatEntity chats = chatRepository.findById(id).orElseThrow(() -> new RuntimeException("Chat not found"));
        ChatResponseDTO chatResponseDTO = ChatResponseDTO.builder()
                .id(chats.getId())
                .destinatarioId(chats.getDestinatario().getId())
                .clienteId(chats.getRemetente().getId())
                .nomeDestinatario(chats.getDestinatario().getNome())
                .ultimaMensagem(chats.getMensagens().isEmpty() ? "" : chats.getMensagens().get(chats.getMensagens().size() - 1).getTexto())
                .mensagensNaoLidas((int) chats.getMensagens().stream()
                        .filter(mensagem -> !StatusMensagem.LIDA.equals(mensagem.getStatus()))
                        .count())
                .build();

        return chatResponseDTO;
    }


}
