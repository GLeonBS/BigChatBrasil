package com.bigchatbrasil.modules.chat.controller;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.config.TestUtils;
import com.bigchatbrasil.modules.chat.dto.CreateChatRequestDTO;
import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.chat.repository.ChatRepository;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.enums.RoleEnum;
import com.bigchatbrasil.modules.cliente.enums.TipoDocumentoEnum;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.cliente.vo.Conta;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.destinatario.repository.DestinatarioRepository;
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.bigchatbrasil.modules.mensagem.repository.MensagemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void createChat() throws Exception {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("Leon LTDA");
        cliente.setDocumento("40089815000103");
        cliente.setTipoDocumento(TipoDocumentoEnum.CNPJ);
        cliente.setNumeroTelefone("11999999999");
        cliente.setSenha("Senha123");
        cliente.setRole(RoleEnum.ROLE_CLIENTE);

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

        mockMvc.perform(post("/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createChatRequestDTO))
        ).andExpect(status().isOk()).andDo(System.out::println);
    }

    @Test
    void getChats() throws Exception {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("Leon LTDA");
        cliente.setDocumento("40089815000103");
        cliente.setTipoDocumento(TipoDocumentoEnum.CNPJ);
        cliente.setNumeroTelefone("11999999999");
        cliente.setSenha("Senha123");
        cliente.setRole(RoleEnum.ROLE_CLIENTE);

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

        ChatEntity chat = Fixtures.createChat(null, clienteSalvo, destinatarioSalvo);
        chatRepository.saveAndFlush(chat);

        mockMvc.perform(get("/chat")
                .requestAttr("cliente_id", clienteSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(System.out::println);
    }

    @Test
    void getChatDetails() throws Exception {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("Leon LTDA");
        cliente.setDocumento("40089815000103");
        cliente.setTipoDocumento(TipoDocumentoEnum.CNPJ);
        cliente.setNumeroTelefone("11999999999");
        cliente.setSenha("Senha123");
        cliente.setRole(RoleEnum.ROLE_CLIENTE);

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

        ChatEntity chat = Fixtures.createChat(null, clienteSalvo, destinatarioSalvo);
        ChatEntity chatSalvo = chatRepository.saveAndFlush(chat);

        mockMvc.perform(get("/chat/" + chatSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(System.out::println);
    }

    @Test
    void getMessages() throws Exception {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("Leon LTDA");
        cliente.setDocumento("40089815000103");
        cliente.setTipoDocumento(TipoDocumentoEnum.CNPJ);
        cliente.setNumeroTelefone("11999999999");
        cliente.setSenha("Senha123");
        cliente.setRole(RoleEnum.ROLE_CLIENTE);

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

        ChatEntity chat = Fixtures.createChat(null, clienteSalvo, destinatarioSalvo);
        ChatEntity chatSalvo = chatRepository.saveAndFlush(chat);

        MensagemEntity mensagem = Fixtures.createMensagem(null, chatSalvo, "Mensagem de teste");
        MensagemEntity mensagem2 = Fixtures.createMensagem(null, chatSalvo, "Mensagem de teste2");
        chatSalvo.getMensagens().addAll(List.of(mensagem, mensagem2));
        mensagemRepository.saveAll(List.of(mensagem, mensagem2));
        chatRepository.saveAndFlush(chatSalvo);


        mockMvc.perform(get("/chat/" + chatSalvo.getId() + "/mensagens")
                .contentType(MediaType.APPLICATION_JSON)
                .requestAttr("cliente_id", clienteSalvo.getId())
        ).andExpect(status().isOk()).andDo(System.out::println);
    }

}