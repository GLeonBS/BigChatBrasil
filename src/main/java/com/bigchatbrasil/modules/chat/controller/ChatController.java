package com.bigchatbrasil.modules.chat.controller;

import com.bigchatbrasil.modules.chat.dto.ChatResponseDTO;
import com.bigchatbrasil.modules.chat.dto.CreateChatRequestDTO;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.chat.useCases.CreateChatUseCase;
import com.bigchatbrasil.modules.chat.useCases.FindAllChatsUseCase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chat")
@AllArgsConstructor
public class ChatController {

    private CreateChatUseCase createChatUseCase;
    private FindAllChatsUseCase findAllChatsUseCase;

    @PostMapping
    public ResponseEntity<ChatEntity> createDestinatario(@RequestBody CreateChatRequestDTO chat) {
        return ResponseEntity.ok(this.createChatUseCase.execute(chat));
    }

    @GetMapping
    public ResponseEntity<List<ChatResponseDTO>> getChats(HttpServletRequest request) {
        Object clienteId = request.getAttribute("cliente_id");
        return ResponseEntity.ok(findAllChatsUseCase.execute(UUID.fromString(clienteId.toString())));
    }

}
