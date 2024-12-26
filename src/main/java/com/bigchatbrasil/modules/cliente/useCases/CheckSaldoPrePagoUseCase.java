package com.bigchatbrasil.modules.cliente.useCases;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.bigchatbrasil.exceptions.SaldoInsuficienteException;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.interfaces.CheckSaldo;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CheckSaldoPrePagoUseCase implements CheckSaldo {

    private ClienteRepository clienteRepository;

    @Override
    public void verificaDescontaSaldoCliente(ClienteEntity cliente) {
        BigDecimal saldo = cliente.getConta().getSaldo();
        if (valorPadrao.compareTo(saldo) > 0) {
            throw new SaldoInsuficienteException("Crédito", saldo);
        }
        descontaSaldoCliente(cliente);
    }

    @Override
    public void descontaSaldoCliente(ClienteEntity cliente) {
        cliente.getConta().setSaldo(cliente.getConta().getSaldo().subtract(valorPadrao));
        clienteRepository.save(cliente);
    }

    @Override
    public PlanoEnum getPlano() {
        return PlanoEnum.PRE_PAGO;
    }
}