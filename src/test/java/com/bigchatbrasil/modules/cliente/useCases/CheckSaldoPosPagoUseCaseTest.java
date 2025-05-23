package com.bigchatbrasil.modules.cliente.useCases;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.exceptions.SaldoInsuficienteException;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
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
    void verificaSaldoCliente() {
        ClienteEntity cliente = Fixtures.createCliente(UUID.randomUUID());
        cliente.getConta().setPlano(PlanoEnum.POS_PAGO);
        cliente.getConta().setSaldo(BigDecimal.TEN);

        Assertions.assertDoesNotThrow(() -> checkSaldoPosPagoUseCase.verificaSaldoCliente(cliente, 1, 0));
    }

    @Test
    void verificaSaldoClienteInvalido() {
        ClienteEntity cliente = Fixtures.createCliente(UUID.randomUUID());
        cliente.getConta().setPlano(PlanoEnum.POS_PAGO);
        cliente.getConta().setLimite(BigDecimal.TEN);
        cliente.getConta().setLimiteConsumido(BigDecimal.TEN);

        assertThatThrownBy(() -> checkSaldoPosPagoUseCase.verificaSaldoCliente(cliente, 1, 0))
                .isInstanceOf(SaldoInsuficienteException.class)
                .hasMessage("Limite insuficiente: 0");
    }

    @Test
    void getPlano() {
        assertThat(checkSaldoPosPagoUseCase.getPlano()).isEqualTo(PlanoEnum.POS_PAGO);
    }
}