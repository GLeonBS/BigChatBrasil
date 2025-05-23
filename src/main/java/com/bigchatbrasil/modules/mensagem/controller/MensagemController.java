package com.bigchatbrasil.modules.mensagem.controller;

import com.bigchatbrasil.modules.mensagem.dto.CreateMensagemRequestDTO;
import com.bigchatbrasil.modules.mensagem.useCases.EnviarMensagensUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mensagem")
@AllArgsConstructor
public class MensagemController {

    private EnviarMensagensUseCase enviarMensagensUseCase;

    @PostMapping
    public ResponseEntity<Void> enviarMensagens(@RequestBody List<CreateMensagemRequestDTO> mensagem) {
        this.enviarMensagensUseCase.execute(mensagem);
        return ResponseEntity.noContent().build();
    }

}
