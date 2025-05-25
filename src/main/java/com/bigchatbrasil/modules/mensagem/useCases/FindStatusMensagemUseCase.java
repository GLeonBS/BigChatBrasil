package com.bigchatbrasil.modules.mensagem.useCases;

import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.enums.StatusMensagem;
import com.bigchatbrasil.modules.mensagem.repository.MensagemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FindStatusMensagemUseCase {

    private final MensagemRepository repository;

    public StatusMensagem execute(UUID mensagemId) {

        MensagemEntity mensagem = repository.findById(mensagemId)
                .orElseThrow(() -> new RuntimeException("Mensagem não encontrada"));

        return mensagem.getStatus();
    }


}
