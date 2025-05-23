package com.bigchatbrasil.modules.cliente.controller;

import com.bigchatbrasil.config.TestUtils;
import com.bigchatbrasil.modules.cliente.dto.ClienteRequestDTO;
import com.bigchatbrasil.modules.cliente.dto.ContaRequestDTO;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.enums.TipoDocumento;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.cliente.vo.Conta;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    void createClienteWithCPFValid() throws Exception {
        ContaRequestDTO contaRequestDTO = ContaRequestDTO.builder()
                .plano(PlanoEnum.POS_PAGO)
                .limite(new BigDecimal("100.00"))
                .build();

        ClienteRequestDTO clienteRequestDTO = ClienteRequestDTO.builder()
                .nome("Nome")
                .documento("19681538021")
                .tipoDocumento(TipoDocumento.CPF)
                .conta(contaRequestDTO)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(clienteRequestDTO))
        ).andExpect(status().isOk()).andDo(System.out::println);
    }

    @Test
    void createClienteWithCNPJValid() throws Exception {
        ContaRequestDTO contaRequestDTO = ContaRequestDTO.builder()
                .plano(PlanoEnum.POS_PAGO)
                .limite(new BigDecimal("100.00"))
                .build();

        ClienteRequestDTO clienteRequestDTO = ClienteRequestDTO.builder()
                .nome("Nome")
                .documento("48783125000161")
                .tipoDocumento(TipoDocumento.CNPJ)
                .conta(contaRequestDTO)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(clienteRequestDTO))
        ).andExpect(status().isOk()).andDo(System.out::println);
    }

    @Test
    void createClienteWithInvalidCPF() throws Exception {
        ContaRequestDTO contaRequestDTO = ContaRequestDTO.builder()
                .plano(PlanoEnum.POS_PAGO)
                .limite(new BigDecimal("100.00"))
                .build();

        ClienteRequestDTO clienteRequestDTO = ClienteRequestDTO.builder()
                .nome("Nome")
                .documento("00000000000")
                .tipoDocumento(TipoDocumento.CPF)
                .conta(contaRequestDTO)
                .build();

        assertThatThrownBy(() -> mockMvc.perform(MockMvcRequestBuilders.post("/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(clienteRequestDTO))));
    }


    @Test
    void createClienteWithInvalidCNPJ() throws Exception {
        ContaRequestDTO contaRequestDTO = ContaRequestDTO.builder()
                .plano(PlanoEnum.POS_PAGO)
                .limite(new BigDecimal("100.00"))
                .build();

        ClienteRequestDTO clienteRequestDTO = ClienteRequestDTO.builder()
                .nome("Nome")
                .documento("00000000000000")
                .tipoDocumento(TipoDocumento.CNPJ)
                .conta(contaRequestDTO)
                .build();

        assertThatThrownBy(() -> mockMvc.perform(MockMvcRequestBuilders.post("/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(clienteRequestDTO))));
    }

    @Test
    void findCliente() throws Exception {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("Leon LTDA");
        cliente.setDocumento("40089815000103");
        cliente.setTipoDocumento(TipoDocumento.CNPJ);


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
        cliente.setNome("Leon LTDA");
        cliente.setDocumento("40089815000103");
        cliente.setTipoDocumento(TipoDocumento.CNPJ);


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
        cliente.setNome("Leon LTDA");
        cliente.setDocumento("40089815000103");
        cliente.setTipoDocumento(TipoDocumento.CNPJ);


        Conta conta = new Conta();
        conta.setPlano(PlanoEnum.PRE_PAGO);
        conta.setSaldo(new BigDecimal("100.00"));

        cliente.setConta(conta);

        ClienteEntity clienteSalvo = repository.saveAndFlush(cliente);

        ContaRequestDTO contaRequestDTO = ContaRequestDTO.builder()
                .plano(PlanoEnum.POS_PAGO)
                .limite(new BigDecimal("100.00"))
                .build();

        ClienteRequestDTO createClienteRequestDTO = ClienteRequestDTO.builder()
                .nome("Leon")
                .conta(contaRequestDTO)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/cliente/" + clienteSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createClienteRequestDTO))
        ).andExpect(status().isOk()).andDo(System.out::println);
    }

    @Test
    void addSaldoCreditoCliente() throws Exception {
        BigDecimal saldo = new BigDecimal("100.00");
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("Leon LTDA");
        cliente.setDocumento("40089815000103");
        cliente.setTipoDocumento(TipoDocumento.CNPJ);


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

    @Test
    void findAllClients() throws Exception {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("Leon LTDA");
        cliente.setDocumento("40089815000103");
        cliente.setTipoDocumento(TipoDocumento.CNPJ);

        Conta conta = new Conta();
        conta.setPlano(PlanoEnum.PRE_PAGO);
        conta.setSaldo(new BigDecimal("100.00"));

        cliente.setConta(conta);

        ClienteEntity cliente2 = new ClienteEntity();
        cliente2.setNome("Augusto LTDA");
        cliente2.setDocumento("99608096090");
        cliente2.setTipoDocumento(TipoDocumento.CPF);

        Conta conta2 = new Conta();
        conta2.setPlano(PlanoEnum.POS_PAGO);
        conta2.setSaldo(new BigDecimal("100.00"));

        cliente2.setConta(conta2);

        repository.saveAllAndFlush(List.of(cliente, cliente2));

        mockMvc.perform(MockMvcRequestBuilders.get("/cliente")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(System.out::println);
    }

    @Test
    void deleteCliente() throws Exception {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("Leon LTDA");
        cliente.setDocumento("40089815000103");
        cliente.setTipoDocumento(TipoDocumento.CNPJ);

        Conta conta = new Conta();
        conta.setPlano(PlanoEnum.PRE_PAGO);
        conta.setSaldo(new BigDecimal("100.00"));
        cliente.setConta(conta);

        ClienteEntity clienteSalvo = repository.saveAndFlush(cliente);

        mockMvc.perform(MockMvcRequestBuilders.delete("/cliente/" + clienteSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent()).andDo(System.out::println);

        ClienteEntity clienteDeletado = repository.findById(clienteSalvo.getId()).orElse(null);


        assertThat(clienteDeletado).isNotNull();
        assertThat(clienteDeletado.getAtivo()).isFalse();
    }
}