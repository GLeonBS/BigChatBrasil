package com.bigchatbrasil.modules.mensagem.useCases;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.mensagem.dto.CreateMensagemRequestDTO;
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.repository.MensagemRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateMensagemUseCase {

    private final MensagemRepository repository;

    private final ChatRepository chatRepository;

    public MensagemEntity execute(CreateMensagemRequestDTO createMensagemRequestDTO) {
        MensagemEntity mensagemEntity = new MensagemEntity();
        BeanUtils.copyProperties(createMensagemRequestDTO, mensagemEntity);
        chatRepository.findById(createMensagemRequestDTO.chatId()).ifPresent(mensagemEntity::setChat);

        return repository.save(mensagemEntity);
    }

}
