package com.bigchatbrasil.modules.destinatario.controller;

import com.bigchatbrasil.modules.destinatario.dto.CreateDestinatarioRequestDTO;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.destinatario.useCases.CreateDestinatarioUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/destinatario")
@AllArgsConstructor
public class DestinatarioController {

    private CreateDestinatarioUseCase createDestinatarioUseCase;

    @PostMapping
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<DestinatarioEntity> createDestinatario(
            @RequestBody CreateDestinatarioRequestDTO destinatario) {
        return ResponseEntity.ok(this.createDestinatarioUseCase.execute(destinatario));
    }

}
