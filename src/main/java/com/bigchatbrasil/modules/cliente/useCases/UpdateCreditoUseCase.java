package com.bigchatbrasil.modules.cliente.useCases;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.cliente.vo.Conta;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UpdateCreditoUseCase {

    private final ClienteRepository repository;
    private final FindClienteUseCase findClienteUseCase;

    public BigDecimal execute(UUID id, BigDecimal saldoToAdd) {
        ClienteEntity cliente = findClienteUseCase.execute(id);

        Conta conta = cliente.getConta();
        if (!PlanoEnum.PRE_PAGO.equals(conta.getPlano())) {
            throw new IllegalArgumentException("Operação não permitida para planos pós-pagos");
        }
        conta.setSaldo(conta.getSaldo().add(saldoToAdd));
        repository.save(cliente);
        return conta.getSaldo();
    }

}
