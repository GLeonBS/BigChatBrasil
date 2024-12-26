package com.bigchatbrasil.modules.destinatario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bigchatbrasil.modules.destinatario.dto.CreateDestinatarioRequestDTO;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.destinatario.useCases.CreateDestinatarioUseCase;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/destinatario")
@AllArgsConstructor
public class DestinatarioController {

    private CreateDestinatarioUseCase createDestinatarioUseCase;

    @PostMapping("/")
    public ResponseEntity<DestinatarioEntity> createDestinatario(
            @RequestBody CreateDestinatarioRequestDTO destinatario) {
        return ResponseEntity.ok(this.createDestinatarioUseCase.execute(destinatario));
    }

}
