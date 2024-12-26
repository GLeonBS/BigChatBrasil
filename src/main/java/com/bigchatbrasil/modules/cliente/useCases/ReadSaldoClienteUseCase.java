package com.bigchatbrasil.modules.cliente.useCases;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bigchatbrasil.exceptions.UserNotFoundException;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReadSaldoClienteUseCase {

    private final ClienteRepository repository;

    public BigDecimal execute(UUID id) {
        return this.repository.findById(id).map(cliente -> cliente.getConta().getSaldo())
                .orElseThrow(UserNotFoundException::new);
    }

}
