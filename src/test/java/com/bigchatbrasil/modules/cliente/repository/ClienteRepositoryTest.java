package com.bigchatbrasil.modules.cliente.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.vo.Conta;

@SpringBootTest
@ActiveProfiles("test")
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @AfterEach
    public void tearDown() {
        clienteRepository.deleteAll();
    }

    @Test
    void findByEmailOrCpfResponsavelOrCnpj() {

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

        clienteRepository.saveAndFlush(cliente);

        ClienteEntity clienteEntity = clienteRepository.findByEmailOrCpfResponsavelOrCnpj("leon@leon.com",
                "19681538021", "40089815000103").get();

        assertThat(clienteEntity).isNotNull();
        assertThat(clienteEntity.getNome()).isEqualTo("Leon");
    }
}