package com.bigchatbrasil.modules.mensagem.useCases;

import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.enums.StatusMensagem;
import com.bigchatbrasil.modules.mensagem.interfaces.EnviarMensagem;
import com.bigchatbrasil.modules.mensagem.repository.MensagemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnviarMensagemSMSUseCase implements EnviarMensagem {

    MensagemRepository mensagemRepository;

    @Override
    public void enviarMensagem(MensagemEntity mensagem) {
        System.out.println("Enviando mensagem via SMS: " + mensagem.getTexto());
        mensagem.setStatus(StatusMensagem.ENVIADA);

        mensagemRepository.save(mensagem);
    }

    @Override
    public boolean viaWhatsapp() {
        return false;
    }
}
