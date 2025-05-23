package com.bigchatbrasil.modules.cliente.entity;

import com.bigchatbrasil.modules.cliente.annotations.DocumentoValido;
import com.bigchatbrasil.modules.cliente.enums.TipoDocumento;
import com.bigchatbrasil.modules.cliente.vo.Conta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@DocumentoValido
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String nome;

    @Column
    private String documento;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @Embedded
    private Conta conta;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @NotNull
    @Column(length = 11, name = "numero_telefone")
    @Size(min = 11, max = 11, message = "O campo telefone deve conter 11 caracteres")
    private String numeroTelefone;

}
