package com.bigchatbrasil.modules.mensagem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bigchatbrasil.modules.mensagem.dto.CreateMensagemRequestDTO;
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.useCases.CreateMensagemUseCase;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/mensagem")
@AllArgsConstructor
public class MensagemController {

    private CreateMensagemUseCase createMensagemUseCase;

    @PostMapping("/")
    public ResponseEntity<MensagemEntity> createMensagem(@RequestBody CreateMensagemRequestDTO mensagem) {
        return ResponseEntity.ok(this.createMensagemUseCase.execute(mensagem));
    }

}
