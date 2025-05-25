package com.bigchatbrasil.modules.mensagem.useCases;

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
                .orElseThrow(() -> new RuntimeException("Mensagem não encontrada"));

        return new MensagemResponseDTO(mensagem.getId(), mensagem.getCliente().getId(), mensagem.getDestinatario().getId(), mensagem.getTexto(), mensagem.getDataHoraEnvio(), mensagem.getPrioridade(), mensagem.getStatus(), mensagem.getCusto());
    }


}
