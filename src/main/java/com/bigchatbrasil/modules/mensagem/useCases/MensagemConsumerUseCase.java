package com.bigchatbrasil.modules.mensagem.useCases;

import com.bigchatbrasil.modules.mensagem.config.MensagemConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class MensagemConsumerUseCase {

    private FindOneMensagemUseCase findOneMensagemUseCase;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(MensagemConfig.NOME_FILA),
            exchange = @Exchange(value = MensagemConfig.NOME_EXCHANGE),
            key = MensagemConfig.ROUTING_KEY))
    public void processMensagem(UUID idMensagem) {


    }
}
