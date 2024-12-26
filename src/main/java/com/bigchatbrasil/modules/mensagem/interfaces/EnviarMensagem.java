package com.bigchatbrasil.modules.mensagem.interfaces;

import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;

public interface EnviarMensagem {
    void enviarMensagem(MensagemEntity mensagem);

    boolean viaWhatsapp();
}
