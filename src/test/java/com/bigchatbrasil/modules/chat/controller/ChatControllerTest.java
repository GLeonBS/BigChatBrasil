package com.bigchatbrasil.modules.chat.controller;

import com.bigchatbrasil.config.TestUtils;
import com.bigchatbrasil.modules.chat.dto.CreateChatRequestDTO;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.enums.Role;
import com.bigchatbrasil.modules.cliente.enums.TipoDocumento;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.cliente.vo.Conta;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.destinatario.repository.DestinatarioRepository;
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

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @AfterEach
    public void tearDown() {
        chatRepository.deleteAll();
        destinatarioRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    @Test
    void createChat() throws Exception {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("Leon LTDA");
        cliente.setDocumento("40089815000103");
        cliente.setTipoDocumento(TipoDocumento.CNPJ);
        cliente.setNumeroTelefone("11999999999");
        cliente.setSenha("Senha123");
        cliente.setRole(Role.ROLE_CLIENTE);

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
                .destinatario(destinatarioSalvo.getId())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createChatRequestDTO))
        ).andExpect(status().isOk()).andDo(System.out::println);
    }

}