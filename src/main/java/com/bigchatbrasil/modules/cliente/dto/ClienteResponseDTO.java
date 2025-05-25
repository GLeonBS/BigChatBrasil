package com.bigchatbrasil.modules.cliente.dto;

import com.bigchatbrasil.modules.cliente.enums.TipoDocumento;
import lombok.*;

import java.util.UUID;

@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteResponseDTO {
    private UUID id;
    private String nome;
    private String documento;
    private TipoDocumento tipoDocumento;
    private ContaRequestDTO conta;
    private boolean ativo;
}
