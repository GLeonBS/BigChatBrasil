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
    public void verificaSaldoCliente(ClienteEntity cliente, int mensagensNormais, int mensagensPrioritarias) {
        BigDecimal saldo = cliente.getConta().getSaldo();
        if (valorNormal.multiply(new BigDecimal(mensagensNormais)).add(valorPrioritario.multiply(new BigDecimal(mensagensPrioritarias))).compareTo(saldo) > 0) {
            throw new SaldoInsuficienteException("Crédito", saldo);
        }
    }

    @Override
    public void descontaSaldoCliente(ClienteEntity cliente, Prioridade prioridade) {
        if (Prioridade.NORMAL.equals(prioridade)) {
            cliente.getConta().setLimiteConsumido(cliente.getConta().getLimiteConsumido().add(valorNormal));
        } else {
            cliente.getConta().setLimiteConsumido(cliente.getConta().getLimiteConsumido().add(valorPrioritario));
        }
        clienteRepository.save(cliente);
    }

    @Override
    public PlanoEnum getPlano() {
        return PlanoEnum.PRE_PAGO;
    }
}
