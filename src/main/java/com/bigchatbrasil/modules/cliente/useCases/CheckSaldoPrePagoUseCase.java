package com.bigchatbrasil.modules.cliente.useCases;

import com.bigchatbrasil.exceptions.SaldoInsuficienteException;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.interfaces.CheckSaldo;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.mensagem.enums.Prioridade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CheckSaldoPrePagoUseCase implements CheckSaldo {

    private ClienteRepository clienteRepository;

    @Override
    public void verificaDescontaSaldoCliente(ClienteEntity cliente, Prioridade prioridade) {
        BigDecimal saldo = cliente.getConta().getSaldo();
        BigDecimal valorUsado = Prioridade.URGENTE == prioridade ?
                valorPrioritario :
                valorNormal;
        if (valorUsado.compareTo(saldo) > 0) {
            throw new SaldoInsuficienteException("Crédito", saldo);
        }
        descontaSaldoCliente(cliente, prioridade, valorUsado);
    }

    @Override
    public void descontaSaldoCliente(ClienteEntity cliente, Prioridade prioridade, BigDecimal valor) {
        cliente.getConta().setCredito(cliente.getConta().getCredito().subtract(valor));
        clienteRepository.save(cliente);
    }

    @Override
    public PlanoEnum getPlano() {
        return PlanoEnum.PRE_PAGO;
    }
}
