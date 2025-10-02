package com.bigchatbrasil.modules.cliente.useCases;

import com.bigchatbrasil.config.Fixtures;
import com.bigchatbrasil.config.TarifaMensagemConfig;
import com.bigchatbrasil.exceptions.SaldoInsuficienteException;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.mensagem.enums.PrioridadeEnum;
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
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class CheckSaldoPrePagoUseCaseTest {
    
    @InjectMocks
    private CheckSaldoPrePagoUseCase checkSaldoPrePagoUseCase;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private TarifaMensagemConfig tarifaMensagemConfig;

    @Test
    void verificaDescontaSaldoCliente() {
        lenient().when(tarifaMensagemConfig.getValorNormal()).thenReturn(new BigDecimal("0.25"));
        lenient().when(tarifaMensagemConfig.getValorPrioritaria()).thenReturn(new BigDecimal("0.50"));

        ClienteEntity cliente = Fixtures.createCliente(UUID.randomUUID());

        Assertions.assertDoesNotThrow(() -> checkSaldoPrePagoUseCase.verificaDescontaSaldoCliente(cliente, PrioridadeEnum.NORMAL));
    }

    @Test
    void verificaDescontaSaldoClienteInvalido() {
        lenient().when(tarifaMensagemConfig.getValorNormal()).thenReturn(new BigDecimal("0.25"));
        lenient().when(tarifaMensagemConfig.getValorPrioritaria()).thenReturn(new BigDecimal("0.50"));

        ClienteEntity cliente = Fixtures.createCliente(UUID.randomUUID());
        cliente.getConta().setSaldo(BigDecimal.ZERO);

        assertThatThrownBy(() -> checkSaldoPrePagoUseCase.verificaDescontaSaldoCliente(cliente, PrioridadeEnum.URGENTE))
                .isInstanceOf(SaldoInsuficienteException.class)
                .hasMessage("Crédito insuficiente: 0");
    }

    @Test
    void getPlano() {
        lenient().when(tarifaMensagemConfig.getValorNormal()).thenReturn(new BigDecimal("0.25"));
        lenient().when(tarifaMensagemConfig.getValorPrioritaria()).thenReturn(new BigDecimal("0.50"));

        assertThat(checkSaldoPrePagoUseCase.getPlano()).isEqualTo(PlanoEnum.PRE_PAGO);
    }
}