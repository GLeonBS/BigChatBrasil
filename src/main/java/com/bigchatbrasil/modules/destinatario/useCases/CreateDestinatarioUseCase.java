package com.bigchatbrasil.modules.destinatario.useCases;

import com.bigchatbrasil.modules.cliente.useCases.FindClienteUseCase;
import com.bigchatbrasil.modules.destinatario.dto.CreateDestinatarioRequestDTO;
import com.bigchatbrasil.modules.destinatario.dto.DestinatarioRespondeDTO;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.destinatario.repository.DestinatarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateDestinatarioUseCase {

    private final DestinatarioRepository repository;

    private final FindClienteUseCase findClienteUseCase;

    public DestinatarioRespondeDTO execute(CreateDestinatarioRequestDTO createDestinatarioRequestDTO) {
        DestinatarioEntity destinatarioEntity = new DestinatarioEntity();
        destinatarioEntity.setCliente(findClienteUseCase.execute(createDestinatarioRequestDTO.clienteId()));
        BeanUtils.copyProperties(createDestinatarioRequestDTO, destinatarioEntity);
        DestinatarioEntity destinatario = repository.save(destinatarioEntity);

        return new DestinatarioRespondeDTO(destinatario.getId(), destinatario.getNome(), destinatario.getNumeroTelefone(),
                destinatario.getCliente().getId());
    }

}
