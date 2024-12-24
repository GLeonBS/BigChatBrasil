package com.backoffice.start;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.backoffice.modules.cliente.entity.ClienteEntity;
import com.backoffice.modules.cliente.enums.PlanoEnum;
import com.backoffice.modules.cliente.repository.ClienteRepository;
import com.backoffice.modules.cliente.vo.Conta;

@Component
public class StartApp implements ApplicationRunner {

    @Autowired
    private ClienteRepository repository;

    @Override
    public void run(org.springframework.boot.ApplicationArguments args) throws Exception {

        ClienteEntity leon = new ClienteEntity();
        leon.setNome("Leon");
        leon.setCpfResponsavel("70912137002");
        leon.setEmail("leon@leon.com");
        leon.setTelefone("12345678901");
        leon.setCnpj("28742150000190");
        leon.setNomeEmpresa("Leon LTDA");

        Conta conta = new Conta();
        conta.setPlano(PlanoEnum.POS_PAGO);
        conta.setLimite(new BigDecimal("1000.00"));

        leon.setConta(conta);

        repository.save(leon);
    }
}
