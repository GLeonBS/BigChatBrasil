package com.bigchatbrasil.modules.chat.repository;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.destinatario.repository.DestinatarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ChatRepositoryTest {

    @Autowired
    private ChatRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DestinatarioRepository destinatarioRepository;

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
        destinatarioRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    @Test
    void findAllByIdIn() {
        ClienteEntity cliente = Fixtures.createCliente(null);
        clienteRepository.saveAndFlush(cliente);

        DestinatarioEntity destinatario = Fixtures.createDestinatario(null, cliente);
        destinatarioRepository.saveAndFlush(destinatario);
        DestinatarioEntity destinatario2 = Fixtures.createDestinatario(null, cliente);
        destinatario2.setNome("Dante");
        DestinatarioEntity destinatario3 = Fixtures.createDestinatario(null, cliente);
        destinatario2.setNome("Vergil");

        destinatarioRepository.saveAllAndFlush(List.of(destinatario, destinatario2, destinatario3));

        ChatEntity chat = Fixtures.createChat(null, cliente, destinatario);
        ChatEntity chat2 = Fixtures.createChat(null, cliente, destinatario2);
        ChatEntity chat3 = Fixtures.createChat(null, cliente, destinatario3);

        repository.saveAllAndFlush(List.of(chat, chat2, chat3));

        List<ChatEntity> chats = repository.findAllByIdIn(List.of(chat.getId(), chat2.getId()));

        assertThat(chats).isNotNull();
        assertThat(chats.size()).isEqualTo(2);
        assertThat(chats).extracting(ChatEntity::getId).containsOnly(chat.getId(), chat2.getId());
    }
}