package com.bigchatbrasil.modules.chat.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Collections;

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

import com.bigchatbrasil.config.TestUtils;
import com.bigchatbrasil.modules.chat.dto.CreateChatRequestDTO;
import com.bigchatbrasil.modules.chat.repository.ChatDestinatarioRepository;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.cliente.vo.Conta;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.destinatario.repository.DestinatarioRepository;

@SpringBootTest
@ActiveProfiles("test")
class ChatControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DestinatarioRepository destinatarioRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatDestinatarioRepository chatDestinatarioRepository;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @AfterEach
    public void tearDown() {
        chatDestinatarioRepository.deleteAll();
        destinatarioRepository.deleteAll();
        chatRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    @Test
    void createChat() throws Exception {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("Leon");
        cliente.setCnpj("40089815000103");
        cliente.setCpfResponsavel("19681538021");
        cliente.setEmail("leon@leon.com");
        cliente.setTelefone("44999999999");
        cliente.setNomeEmpresa("Leon LTDA");

        Conta conta = new Conta();
        conta.setPlano(PlanoEnum.PRE_PAGO);
        conta.setSaldo(new BigDecimal("100.00"));

        cliente.setConta(conta);

        ClienteEntity clienteSalvo = clienteRepository.saveAndFlush(cliente);

        DestinatarioEntity destinatario = new DestinatarioEntity();
        destinatario.setNome("Leon");
        destinatario.setNumeroTelefone("44999999999");
        destinatario.setCliente(clienteSalvo);

        DestinatarioEntity destinatarioSalvo = destinatarioRepository.saveAndFlush(destinatario);

        CreateChatRequestDTO createChatRequestDTO = CreateChatRequestDTO
                .builder()
                .remetente(clienteSalvo.getId())
                .destinatarios(Collections.singletonList(destinatarioSalvo.getId()))
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/chat/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createChatRequestDTO))
        ).andExpect(status().isOk()).andDo(System.out::println);
    }

}