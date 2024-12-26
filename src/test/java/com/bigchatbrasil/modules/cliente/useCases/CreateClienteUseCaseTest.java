package com.bigchatbrasil.modules.cliente.useCases;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bigchatbrasil.exceptions.UserFoundException;
import com.bigchatbrasil.modules.cliente.dto.ContaRequestDTO;
import com.bigchatbrasil.modules.cliente.dto.CreateClienteRequestDTO;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.enums.PlanoEnum;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.cliente.vo.Conta;

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

        CreateClienteRequestDTO createClienteRequestDTO = CreateClienteRequestDTO.builder()
                .nome("Nome")
                .email("leon@leon.com")
                .cpfResponsavel("12345678901")
                .cnpj("12345678901234")
                .conta(contaRequestDTO)
                .build();

        Conta conta = new Conta(contaRequestDTO.plano(), contaRequestDTO.limite(), contaRequestDTO.credito(),
                BigDecimal.ZERO);

        ClienteEntity cliente = new ClienteEntity(UUID.randomUUID(), createClienteRequestDTO.nome(),
                createClienteRequestDTO.email(),
                createClienteRequestDTO.telefone(), createClienteRequestDTO.cpfResponsavel(),
                createClienteRequestDTO.cnpj(),
                createClienteRequestDTO.nomeEmpresa(), conta);

        when(repository.findByEmailOrCpfResponsavelOrCnpj(any(), any(), any())).thenReturn(Optional.empty());

        when(repository.save(any(ClienteEntity.class))).thenReturn(cliente);

        Assertions.assertDoesNotThrow(() -> createClienteUseCase.execute(createClienteRequestDTO));
    }

    @Test
    void shouldBeThrowUserFoundException() {

        ContaRequestDTO contaRequestDTO = ContaRequestDTO.builder()
                .plano(PlanoEnum.POS_PAGO)
                .limite(new BigDecimal("100.00"))
                .build();

        CreateClienteRequestDTO createClienteRequestDTO = CreateClienteRequestDTO.builder()
                .nome("Nome")
                .email("leon@leon.com")
                .cpfResponsavel("12345678901")
                .cnpj("12345678901234")
                .conta(contaRequestDTO)
                .build();

        Conta conta = new Conta(contaRequestDTO.plano(), contaRequestDTO.limite(), contaRequestDTO.credito(),
                BigDecimal.ZERO);

        ClienteEntity cliente = new ClienteEntity(UUID.randomUUID(), createClienteRequestDTO.nome(),
                createClienteRequestDTO.email(),
                createClienteRequestDTO.telefone(), createClienteRequestDTO.cpfResponsavel(),
                createClienteRequestDTO.cnpj(),
                createClienteRequestDTO.nomeEmpresa(), conta);

        when(repository.findByEmailOrCpfResponsavelOrCnpj(any(), any(), any())).thenReturn(Optional.of(cliente));

        assertThatThrownBy(() -> createClienteUseCase.execute(createClienteRequestDTO))
                .isInstanceOf(UserFoundException.class);
    }

}