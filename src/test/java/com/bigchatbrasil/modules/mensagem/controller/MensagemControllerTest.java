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
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
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
        chatRepository.deleteAll();
        destinatarioRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    @Test
    void enviarMensagens() throws Exception {

        ClienteEntity clienteSalvo = clienteRepository.saveAndFlush(Fixtures.createCliente(null));

        DestinatarioEntity destinatario = destinatarioRepository.saveAndFlush(Fixtures.createDestinatario(null, clienteSalvo));
        DestinatarioEntity destinatario2 = destinatarioRepository.saveAndFlush(Fixtures.createDestinatario(null, clienteSalvo));

        ChatEntity chatSalvo = chatRepository.saveAndFlush(Fixtures.createChat(null, clienteSalvo, destinatario));
        ChatEntity chatSalvo2 = chatRepository.saveAndFlush(Fixtures.createChat(null, clienteSalvo, destinatario2));

        CreateMensagemRequestDTO createMensagemRequestDTO = new CreateMensagemRequestDTO(chatSalvo.getId(), destinatario2.getId(), "Teste", Prioridade.NORMAL,
                false);
        CreateMensagemRequestDTO createMensagemRequestDTO2 = new CreateMensagemRequestDTO(chatSalvo2.getId(), destinatario2.getId(), "Teste", Prioridade.URGENTE,
                true);

        mockMvc.perform(MockMvcRequestBuilders.post("/mensagem")
                .requestAttr("cliente_id", clienteSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createMensagemRequestDTO))
        ).andExpect(status().isCreated()).andDo(System.out::println);

        mockMvc.perform(MockMvcRequestBuilders.post("/mensagem")
                .requestAttr("cliente_id", clienteSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createMensagemRequestDTO2))
        ).andExpect(status().isCreated()).andDo(System.out::println);
    }

    @Test
    void buscarMensagem() throws Exception {
        ClienteEntity clienteSalvo = clienteRepository.saveAndFlush(Fixtures.createCliente(null));

        DestinatarioEntity destinatario = destinatarioRepository.saveAndFlush(Fixtures.createDestinatario(null, clienteSalvo));

        ChatEntity chatSalvo = chatRepository.saveAndFlush(Fixtures.createChat(null, clienteSalvo, destinatario));

        MensagemEntity mensagemSalva = mensagemRepository.saveAndFlush(Fixtures.createMensagem(null, chatSalvo, "Teste mensagem"));

        mockMvc.perform(MockMvcRequestBuilders.get("/mensagem/" + mensagemSalva.getId())
                .requestAttr("cliente_id", clienteSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(System.out::println);
    }

}