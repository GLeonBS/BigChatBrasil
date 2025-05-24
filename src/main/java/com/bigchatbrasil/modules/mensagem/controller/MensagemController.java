package com.bigchatbrasil.modules.mensagem.controller;

import com.bigchatbrasil.modules.mensagem.dto.CreateMensagemRequestDTO;
import com.bigchatbrasil.modules.mensagem.useCases.EnviarMensagensUseCase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mensagem")
@AllArgsConstructor
public class MensagemController {

    private EnviarMensagensUseCase enviarMensagensUseCase;

    @PostMapping
    public ResponseEntity<Void> enviarMensagens(HttpServletRequest request, @RequestBody List<CreateMensagemRequestDTO> mensagem) {
        Object clienteId = request.getAttribute("cliente_id");
        this.enviarMensagensUseCase.execute(mensagem, UUID.fromString(clienteId.toString()));
        return ResponseEntity.noContent().build();
    }

}
