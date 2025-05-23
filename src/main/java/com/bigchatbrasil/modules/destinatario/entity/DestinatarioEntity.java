package com.bigchatbrasil.modules.destinatario.entity;

import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "destinatario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class DestinatarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    @NotNull
    @Column(name = "numero_telefone", length = 11)
    @Size(min = 11, max = 11, message = "O campo telefone deve conter 11 caracteres")
    private String numeroTelefone;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

}
