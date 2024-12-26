package com.bigchatbrasil.modules.chat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bigchatbrasil.modules.chat.dto.CreateChatRequestDTO;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.chat.useCases.CreateChatUseCase;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/chat")
@AllArgsConstructor
public class ChatController {

    private CreateChatUseCase createChatUseCase;

    @PostMapping("/")
    public ResponseEntity<ChatEntity> createDestinatario(@RequestBody CreateChatRequestDTO chat) {
        return ResponseEntity.ok(this.createChatUseCase.execute(chat));
    }

}
