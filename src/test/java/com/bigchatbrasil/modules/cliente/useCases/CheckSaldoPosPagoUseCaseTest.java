package com.bigchatbrasil.modules.cliente.useCases;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.exceptions.SaldoInsuficienteException;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.mensagem.enums.Prioridade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class CheckSaldoPosPagoUseCaseTest {

    @InjectMocks
    private CheckSaldoPosPagoUseCase checkSaldoPosPagoUseCase;

    @Mock
    private ClienteRepository clienteRepository;

    @Test
    void verificaDescontaSaldoCliente() {
        ClienteEntity cliente = Fixtures.createCliente(UUID.randomUUID());
        cliente.getConta().setPlano(PlanoEnum.POS_PAGO);
        cliente.getConta().setSaldo(BigDecimal.TEN);

        Assertions.assertDoesNotThrow(() -> checkSaldoPosPagoUseCase.verificaDescontaSaldoCliente(cliente, Prioridade.NORMAL));
    }

    @Test
    void verificaDescontaSaldoClienteInvalido() {
        ClienteEntity cliente = Fixtures.createCliente(UUID.randomUUID());
        cliente.getConta().setPlano(PlanoEnum.POS_PAGO);
        cliente.getConta().setLimite(BigDecimal.TEN);
        cliente.getConta().setLimiteConsumido(BigDecimal.TEN);

        assertThatThrownBy(() -> checkSaldoPosPagoUseCase.verificaDescontaSaldoCliente(cliente, Prioridade.URGENTE))
                .isInstanceOf(SaldoInsuficienteException.class)
                .hasMessage("Limite insuficiente: 0");
    }

    @Test
    void getPlano() {
        assertThat(checkSaldoPosPagoUseCase.getPlano()).isEqualTo(PlanoEnum.POS_PAGO);
    }
}