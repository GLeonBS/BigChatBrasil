package com.bigchatbrasil.modules.mensagem.useCases;

import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.interfaces.EnviarMensagem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnviarMensagemWhatsappUseCase implements EnviarMensagem {

    @Override
    public void enviarMensagem(MensagemEntity mensagem) {
        System.out.println("Enviando mensagem via Whatsapp: " + mensagem.getTexto());
    }

    @Override
    public boolean viaWhatsapp() {
        return true;
    }
}
