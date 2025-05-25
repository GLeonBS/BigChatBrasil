package com.bigchatbrasil.modules.cliente.useCases;

import com.bigchatbrasil.exceptions.UserNotFoundException;
import com.bigchatbrasil.modules.cliente.dto.ClienteResponseDTO;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FindClienteUseCase {

    private final ClienteRepository repository;

    public ClienteEntity execute(UUID id) {
        return this.repository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public ClienteResponseDTO executeResponse(UUID id) {
        ClienteEntity clienteEncontrado = this.execute(id);

        return ClienteResponseDTO.from(clienteEncontrado);

    }

}
