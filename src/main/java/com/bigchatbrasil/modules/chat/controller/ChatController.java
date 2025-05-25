package com.bigchatbrasil.modules.chat.controller;

import com.bigchatbrasil.modules.chat.dto.ChatResponseDTO;
import com.bigchatbrasil.modules.chat.dto.CreateChatRequestDTO;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.chat.useCases.CreateChatUseCase;
import com.bigchatbrasil.modules.chat.useCases.FindAllChatsUseCase;
import com.bigchatbrasil.modules.chat.useCases.FindMessagesChatUseCase;
import com.bigchatbrasil.modules.chat.useCases.FindOneChatUseCase;
import com.bigchatbrasil.modules.mensagem.dto.MensagemResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    private FindOneChatUseCase findOneChatUseCase;
    private FindMessagesChatUseCase findMessagesChatUseCase;

    @PostMapping
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<ChatEntity> createDestinatario(@RequestBody CreateChatRequestDTO chat) {
        return ResponseEntity.ok(this.createChatUseCase.execute(chat));
    }

    @GetMapping
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<List<ChatResponseDTO>> getChats(HttpServletRequest request) {
        Object clienteId = request.getAttribute("cliente_id");
        return ResponseEntity.ok(findAllChatsUseCase.execute(UUID.fromString(clienteId.toString())));
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<ChatResponseDTO> getChatDetails(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(findOneChatUseCase.execute(id));
    }

    @GetMapping("/{id}/mensagens")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<List<MensagemResponseDTO>> getMessages(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(findMessagesChatUseCase.execute(id));
    }

}
