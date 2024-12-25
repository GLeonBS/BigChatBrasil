package com.bigchatbrasil.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.bigchatbrasil.modules.cliente.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.destinatario.repository.DestinatarioRepository;

@Component
public class StartApp implements ApplicationRunner {

    @Autowired
    private DestinatarioRepository repository;

    @Override
    public void run(org.springframework.boot.ApplicationArguments args) throws Exception {

        DestinatarioEntity destinatario = new DestinatarioEntity();
        destinatario.setNome("Leonardo");
        destinatario.setNumeroTelefone("44999999999");

        repository.save(destinatario);
    }
}
