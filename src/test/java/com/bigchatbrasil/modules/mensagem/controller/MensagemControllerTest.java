package com.bigchatbrasil.modules.mensagem.controller;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.config.TestUtils;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.destinatario.repository.DestinatarioRepository;
import com.bigchatbrasil.modules.mensagem.dto.CreateMensagemRequestDTO;
import com.bigchatbrasil.modules.mensagem.enums.Prioridade;
import com.bigchatbrasil.modules.mensagem.repository.MensagemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class MensagemControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DestinatarioRepository destinatarioRepository;

    @Autowired
    private MensagemRepository mensagemRepository;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @AfterEach
    public void tearDown() {
        mensagemRepository.deleteAll();
        destinatarioRepository.deleteAll();
        chatRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    @Test
    void enviarMensagem() throws Exception {

        ClienteEntity cliente = Fixtures.createCliente(null);

        ClienteEntity clienteSalvo = clienteRepository.saveAndFlush(cliente);

        DestinatarioEntity destinatario = Fixtures.createDestinatario(null, clienteSalvo);

        DestinatarioEntity destinatarioSalvo = destinatarioRepository.saveAndFlush(destinatario);

        ChatEntity chat = new ChatEntity();
        chat.setRemetente(clienteSalvo);

        ChatEntity chatSalvo = chatRepository.saveAndFlush(chat);

        CreateMensagemRequestDTO createMensagemRequestDTO = new CreateMensagemRequestDTO(chatSalvo.getId(), "Teste", Prioridade.NORMAL,
                "44999999999", false);

        mockMvc.perform(MockMvcRequestBuilders.post("/mensagem")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createMensagemRequestDTO))
        ).andExpect(status().isOk()).andDo(System.out::println);
    }

}