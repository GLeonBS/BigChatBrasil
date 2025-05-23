package com.bigchatbrasil.modules.mensagem.controller;

import com.bigchatbrasil.modules.mensagem.dto.CreateMensagemRequestDTO;
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.useCases.EnviarMensagemUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mensagem")
@AllArgsConstructor
public class MensagemController {

    private EnviarMensagemUseCase enviarMensagemUseCase;

    @PostMapping
    public ResponseEntity<MensagemEntity> enviarMensagem(@RequestBody CreateMensagemRequestDTO mensagem) {
        return ResponseEntity.ok(this.enviarMensagemUseCase.execute(mensagem));
    }

}
