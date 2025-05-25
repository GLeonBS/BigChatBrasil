package com.bigchatbrasil.modules.mensagem.controller;

import com.bigchatbrasil.modules.mensagem.dto.CreateMensagemRequestDTO;
import com.bigchatbrasil.modules.mensagem.dto.MensagemResponseDTO;
import com.bigchatbrasil.modules.mensagem.enums.StatusMensagem;
import com.bigchatbrasil.modules.mensagem.useCases.EnviarMensagensUseCase;
import com.bigchatbrasil.modules.mensagem.useCases.FindOneMensagemUseCase;
import com.bigchatbrasil.modules.mensagem.useCases.FindStatusMensagemUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/mensagem")
@AllArgsConstructor
public class MensagemController {

    private EnviarMensagensUseCase enviarMensagensUseCase;
    private FindOneMensagemUseCase findOneMensagemUseCase;
    private FindStatusMensagemUseCase findStatusMensagemUseCase;

    @PostMapping
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<MensagemResponseDTO> enviarMensagens(HttpServletRequest request, @RequestBody CreateMensagemRequestDTO mensagem) {
        Object clienteId = request.getAttribute("cliente_id");
        MensagemResponseDTO mensagemResponseDTO = this.enviarMensagensUseCase.execute(mensagem, UUID.fromString(clienteId.toString()));
        return ResponseEntity.status(HttpStatus.CREATED).body(mensagemResponseDTO);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<MensagemResponseDTO> buscarMensagem(@PathVariable UUID id) {
        MensagemResponseDTO mensagemResponseDTO = this.findOneMensagemUseCase.execute(id);
        return ResponseEntity.ok(mensagemResponseDTO);
    }

    @GetMapping("/{id}/status")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<StatusMensagem> buscarStatus(@PathVariable UUID id) {
        return ResponseEntity.ok(this.findStatusMensagemUseCase.execute(id));
    }

}
