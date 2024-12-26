package com.bigchatbrasil.modules.destinatario.useCases;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bigchatbrasil.modules.cliente.useCases.FindClienteUseCase;
import com.bigchatbrasil.modules.destinatario.dto.CreateDestinatarioRequestDTO;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.destinatario.repository.DestinatarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateDestinatarioUseCase {

    private final DestinatarioRepository repository;

    private final FindClienteUseCase findClienteUseCase;

    public DestinatarioEntity execute(CreateDestinatarioRequestDTO createDestinatarioRequestDTO) {
        DestinatarioEntity destinatarioEntity = new DestinatarioEntity();
        destinatarioEntity.setCliente(findClienteUseCase.execute(createDestinatarioRequestDTO.clienteId()));
        BeanUtils.copyProperties(createDestinatarioRequestDTO, destinatarioEntity);

        return repository.save(destinatarioEntity);
    }

}
