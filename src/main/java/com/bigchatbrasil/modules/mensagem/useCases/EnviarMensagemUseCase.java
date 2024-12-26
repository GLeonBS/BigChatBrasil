package com.bigchatbrasil.modules.mensagem.useCases;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.interfaces.CheckSaldo;
import com.bigchatbrasil.modules.mensagem.dto.CreateMensagemRequestDTO;
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.interfaces.EnviarMensagem;
import com.bigchatbrasil.modules.mensagem.repository.MensagemRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EnviarMensagemUseCase {

    private final MensagemRepository repository;

    private final ChatRepository chatRepository;

    private final List<CheckSaldo> estrategiasSaldos;

    private final List<EnviarMensagem> estrategiaEnvio;

    public MensagemEntity execute(CreateMensagemRequestDTO createMensagemRequestDTO) {
        MensagemEntity mensagemEntity = new MensagemEntity();
        BeanUtils.copyProperties(createMensagemRequestDTO, mensagemEntity);
        chatRepository.findById(createMensagemRequestDTO.chatId()).ifPresent(mensagemEntity::setChat);
        ClienteEntity cliente = mensagemEntity.getChat().getRemetente();

        CheckSaldo checkSaldo = estrategiasSaldos.stream()
                .filter(estrategia -> cliente.getConta().getPlano().equals(estrategia.getPlano()))
                .findFirst().orElseThrow(() -> new RuntimeException("Plano não encontrado"));

        checkSaldo.verificaDescontaSaldoCliente(cliente);

        EnviarMensagem enviarMensagem = estrategiaEnvio.stream()
                .filter(estrategia -> mensagemEntity.isWhatsapp() == estrategia.viaWhatsapp())
                .findFirst().orElseThrow(() -> new RuntimeException("Estratégia de envio não encontrada"));

        enviarMensagem.enviarMensagem(mensagemEntity);

        return repository.save(mensagemEntity);
    }

}
