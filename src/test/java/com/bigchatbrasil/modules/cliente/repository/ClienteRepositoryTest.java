package com.bigchatbrasil.modules.cliente.repository;

import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.enums.Role;
import com.bigchatbrasil.modules.cliente.enums.TipoDocumento;
import com.bigchatbrasil.modules.cliente.vo.Conta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    void findByDocumento() {

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

        clienteRepository.saveAndFlush(cliente);

        ClienteEntity clienteEntity = clienteRepository.findByDocumento("40089815000103").get();

        assertThat(clienteEntity).isNotNull();
        assertThat(clienteEntity.getNome()).isEqualTo("Leon LTDA");
    }
}