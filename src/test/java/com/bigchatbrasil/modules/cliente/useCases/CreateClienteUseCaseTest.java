package com.bigchatbrasil.modules.cliente.useCases;

import com.bigchatbrasil.exceptions.UserFoundException;
import com.bigchatbrasil.modules.cliente.dto.ClienteRequestDTO;
import com.bigchatbrasil.modules.cliente.dto.ContaRequestDTO;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.enums.TipoDocumento;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.cliente.vo.Conta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateClienteUseCaseTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private CreateClienteUseCase createClienteUseCase;

    @Test
    void shouldBeCreateCliente() {

        ContaRequestDTO contaRequestDTO = ContaRequestDTO.builder()
                .plano(PlanoEnum.POS_PAGO)
                .limite(new BigDecimal("100.00"))
                .build();

        ClienteRequestDTO clienteRequestDTO = ClienteRequestDTO.builder()
                .nome("Nome")
                .documento("12345678901234")
                .tipoDocumento(TipoDocumento.CNPJ)
                .conta(contaRequestDTO)
                .numeroTelefone("44999999999")
                .build();

        Conta conta = new Conta(contaRequestDTO.plano(), contaRequestDTO.limite(), contaRequestDTO.credito(),
                BigDecimal.ZERO);

        ClienteEntity cliente = new ClienteEntity(UUID.randomUUID(), clienteRequestDTO.nome(),
                clienteRequestDTO.documento(),
                clienteRequestDTO.tipoDocumento(),
                conta, true, clienteRequestDTO.numeroTelefone());

        when(repository.findByDocumento(any())).thenReturn(Optional.empty());

        when(repository.save(any(ClienteEntity.class))).thenReturn(cliente);

        Assertions.assertDoesNotThrow(() -> createClienteUseCase.execute(clienteRequestDTO));
    }

    @Test
    void shouldBeThrowUserFoundException() {

        ContaRequestDTO contaRequestDTO = ContaRequestDTO.builder()
                .plano(PlanoEnum.POS_PAGO)
                .limite(new BigDecimal("100.00"))
                .build();

        ClienteRequestDTO clienteRequestDTO = ClienteRequestDTO.builder()
                .nome("Nome")
                .documento("12345678901234")
                .tipoDocumento(TipoDocumento.CNPJ)
                .conta(contaRequestDTO)
                .numeroTelefone("44999999999")
                .build();

        Conta conta = new Conta(contaRequestDTO.plano(), contaRequestDTO.limite(), contaRequestDTO.credito(),
                BigDecimal.ZERO);

        ClienteEntity cliente = new ClienteEntity(UUID.randomUUID(), clienteRequestDTO.nome(),
                clienteRequestDTO.documento(),
                clienteRequestDTO.tipoDocumento(),
                conta, true, clienteRequestDTO.numeroTelefone());

        when(repository.findByDocumento(any())).thenReturn(Optional.of(cliente));

        assertThatThrownBy(() -> createClienteUseCase.execute(clienteRequestDTO))
                .isInstanceOf(UserFoundException.class);
    }

}