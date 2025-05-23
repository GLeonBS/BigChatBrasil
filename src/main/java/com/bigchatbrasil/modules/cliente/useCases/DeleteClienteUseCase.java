package com.bigchatbrasil.modules.cliente.useCases;

import com.bigchatbrasil.exceptions.UserNotFoundException;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeleteClienteUseCase {

    private final ClienteRepository repository;

    public void execute(UUID id) {
        ClienteEntity cliente = this.repository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        cliente.setAtivo(false);
        this.repository.save(cliente);
    }
}
