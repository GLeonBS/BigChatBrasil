package com.bigchatbrasil.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.repository.MensagemRepository;

@Component
public class StartApp implements ApplicationRunner {

    @Autowired
    private MensagemRepository repository;

    @Override
    public void run(org.springframework.boot.ApplicationArguments args) throws Exception {

        MensagemEntity mensagem = new MensagemEntity();
        mensagem.setTexto("Oilá como vai?");
        mensagem.setNumeroTelefone("99999999999");

        repository.save(mensagem);
    }
}
