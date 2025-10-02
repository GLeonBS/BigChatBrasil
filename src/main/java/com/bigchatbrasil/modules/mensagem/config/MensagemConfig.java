package com.bigchatbrasil.modules.mensagem.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MensagemConfig {

    public static final String NOME_FILA = "FilaMensagens";
    public static final String NOME_EXCHANGE = "ExchangeMensagens";
    public static final String ROUTING_KEY = "EnviarMensagens";

    @Bean
    DirectExchange mensagemExchange() {
        return new DirectExchange(NOME_EXCHANGE);
    }

    @Bean
    Queue filaMensagens() {
        return QueueBuilder.durable(NOME_FILA)
                .withArgument("x-max-priority", 10)
                .build();
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(filaMensagens()).to(mensagemExchange()).with(ROUTING_KEY);
    }
}
