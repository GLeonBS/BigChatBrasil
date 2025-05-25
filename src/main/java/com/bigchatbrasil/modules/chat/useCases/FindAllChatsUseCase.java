package com.bigchatbrasil.modules.chat.useCases;

import com.bigchatbrasil.modules.chat.dto.ChatResponseDTO;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.mensagem.enums.StatusMensagem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FindAllChatsUseCase {
    private ChatRepository chatRepository;

    public List<ChatResponseDTO> execute(UUID id) {
        List<ChatEntity> chats = chatRepository.findAllByRemetenteId(id);
        List<ChatResponseDTO> chatResponseDTOs = chats.stream()
                .map(chat -> ChatResponseDTO.builder()
                        .id(chat.getId())
                        .clienteId(chat.getRemetente().getId())
                        .destinatarioId(chat.getDestinatario().getId())
                        .nomeDestinatario(chat.getDestinatario().getNome())
                        .ultimaMensagem(chat.getMensagens().isEmpty() ? "" : chat.getMensagens().get(chat.getMensagens().size() - 1).getTexto())
                        .mensagensNaoLidas((int) chat.getMensagens().stream().filter(mensagem -> !StatusMensagem.LIDA.equals(mensagem.getStatus())).count())
                        .build())
                .toList();
        return chatResponseDTOs;
    }


}
