package com.bigchatbrasil.modules.destinatario.controller;

import com.bigchatbrasil.config.TestUtils;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.enums.TipoDocumento;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.cliente.vo.Conta;
import com.bigchatbrasil.modules.destinatario.dto.CreateDestinatarioRequestDTO;
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
class DestinatarioControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DestinatarioRepository destinatarioRepository;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @AfterEach
    public void tearDown() {
        destinatarioRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    @Test
    void createDestinatario() throws Exception {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("Leon");
        cliente.setDocumento("40089815000103");
        cliente.setTipoDocumento(TipoDocumento.CNPJ);
        cliente.setNumeroTelefone("11999999999");

        Conta conta = new Conta();
        conta.setPlano(PlanoEnum.PRE_PAGO);
        conta.setSaldo(new BigDecimal("100.00"));

        cliente.setConta(conta);

        ClienteEntity clienteSalvo = clienteRepository.saveAndFlush(cliente);

        CreateDestinatarioRequestDTO createDestinatarioRequestDTO = CreateDestinatarioRequestDTO.builder()
                .nome("Leon")
                .numeroTelefone("44999999999")
                .clienteId(clienteSalvo.getId())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/destinatario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createDestinatarioRequestDTO))
        ).andExpect(status().isOk()).andDo(System.out::println);
    }
}