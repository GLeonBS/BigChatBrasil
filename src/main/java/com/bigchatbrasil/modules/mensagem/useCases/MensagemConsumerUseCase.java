package com.bigchatbrasil.modules.mensagem.useCases;

import com.bigchatbrasil.exceptions.EstrategiaEnvioNotFoundException;
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.enums.StatusMensagem;
import com.bigchatbrasil.modules.mensagem.interfaces.EnviarMensagem;
import com.bigchatbrasil.modules.mensagem.repository.MensagemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class MensagemConsumerUseCase {

    private final List<EnviarMensagem> estrategiaEnvio;
    private MensagemRepository repository;

    @RabbitListener(queues = "#{filaMensagens.name}")
    public void processMensagem(MensagemEntity mensagem) {
        System.out.println("Mensagem recebida: " + mensagem.getTexto());
        try {
            mensagem.setStatus(StatusMensagem.PROCESSANDO);
            repository.save(mensagem);
            Thread.sleep(1000);

            EnviarMensagem enviarMensagem = estrategiaEnvio.stream()
                    .filter(estrategia -> mensagem.isWhatsapp() == estrategia.viaWhatsapp())
                    .findFirst().orElseThrow(EstrategiaEnvioNotFoundException::new);
            enviarMensagem.enviarMensagem(mensagem);
            mensagem.setStatus(StatusMensagem.ENVIADA);
            repository.save(mensagem);
            Thread.sleep(1000);

            mensagem.setStatus(StatusMensagem.ENTREGUE);
            repository.save(mensagem);
            Thread.sleep(1000);

            mensagem.setStatus(StatusMensagem.LIDA);
            repository.save(mensagem);
            System.out.println("Mensagem lida com sucesso.");

        } catch (Exception e) {
            mensagem.setStatus(StatusMensagem.ERRO);
            repository.save(mensagem);
            System.err.println("Erro ao processar mensagem: " + e.getMessage());
        }
    }
}
