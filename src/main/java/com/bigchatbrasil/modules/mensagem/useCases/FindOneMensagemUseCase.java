package com.bigchatbrasil.modules.mensagem.useCases;

import com.bigchatbrasil.exceptions.MensagemNotFoundException;
import com.bigchatbrasil.modules.mensagem.dto.MensagemResponseDTO;
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.repository.MensagemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FindOneMensagemUseCase {

    private final MensagemRepository repository;

    public MensagemResponseDTO execute(UUID mensagemId) {

        MensagemEntity mensagem = repository.findById(mensagemId)
                .orElseThrow(MensagemNotFoundException::new);

        return MensagemResponseDTO.from(mensagem);
    }


}
