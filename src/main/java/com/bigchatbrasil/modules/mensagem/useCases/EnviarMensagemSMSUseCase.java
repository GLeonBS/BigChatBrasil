package com.bigchatbrasil.modules.mensagem.useCases;

import org.springframework.stereotype.Service;

import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.interfaces.EnviarMensagem;

@Service
public class EnviarMensagemSMSUseCase implements EnviarMensagem {

    @Override
    public void enviarMensagem(MensagemEntity mensagem) {
        System.out.println("Enviando mensagem via SMS: " + mensagem.getTexto());
    }

    @Override
    public boolean viaWhatsapp() {
        return false;
    }
}