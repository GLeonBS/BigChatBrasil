package com.bigchatbrasil.modules.cliente.useCases;

import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindAllClientsUseCase {

    private final ClienteRepository repository;

    public List<ClienteEntity> execute() {
        return this.repository.findAll();
    }
}
