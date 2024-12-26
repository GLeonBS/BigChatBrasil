package com.bigchatbrasil.modules.cliente.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

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
import com.bigchatbrasil.modules.cliente.dto.ContaRequestDTO;
import com.bigchatbrasil.modules.cliente.dto.CreateClienteRequestDTO;
import com.bigchatbrasil.modules.cliente.dto.UpdateClienteRequestDTO;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.cliente.vo.Conta;

@SpringBootTest
@ActiveProfiles("test")
class ClienteControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ClienteRepository repository;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void createCliente() throws Exception {
        ContaRequestDTO contaRequestDTO = ContaRequestDTO.builder()
                .plano(PlanoEnum.POS_PAGO)
                .limite(new BigDecimal("100.00"))
                .build();

        CreateClienteRequestDTO createClienteRequestDTO = CreateClienteRequestDTO.builder()
                .nome("Nome")
                .email("leon@leon.com")
                .cpfResponsavel("19681538021")
                .cnpj("40089815000103")
                .conta(contaRequestDTO)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createClienteRequestDTO))
        ).andExpect(status().isOk()).andDo(System.out::println);
    }

    @Test
    void readCliente() throws Exception {
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

        ClienteEntity clienteSalvo = repository.saveAndFlush(cliente);

        mockMvc.perform(MockMvcRequestBuilders.get("/cliente/" + clienteSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(System.out::println);
    }

    @Test
    void readSaldoCliente() throws Exception {
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

        ClienteEntity clienteSalvo = repository.saveAndFlush(cliente);

        mockMvc.perform(MockMvcRequestBuilders.get("/cliente/saldo/" + clienteSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(System.out::println);
    }

    @Test
    void updateCliente() throws Exception {
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

        ClienteEntity clienteSalvo = repository.saveAndFlush(cliente);

        ContaRequestDTO contaRequestDTO = ContaRequestDTO.builder()
                .plano(PlanoEnum.POS_PAGO)
                .limite(new BigDecimal("100.00"))
                .build();

        UpdateClienteRequestDTO createClienteRequestDTO = UpdateClienteRequestDTO.builder()
                .id(clienteSalvo.getId())
                .nome("Leon")
                .conta(contaRequestDTO)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/cliente/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createClienteRequestDTO))
        ).andExpect(status().isOk()).andDo(System.out::println);
    }

    @Test
    void addSaldoCreditoCliente() throws Exception {
        BigDecimal saldo = new BigDecimal("100.00");
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

        ClienteEntity clienteSalvo = repository.saveAndFlush(cliente);

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/add-saldo-credito/" + clienteSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(saldo))
        ).andExpect(status().isOk()).andDo(System.out::println);
    }
}