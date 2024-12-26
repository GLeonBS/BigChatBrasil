package com.bigchatbrasil.modules.destinatario.entity;

import java.util.List;
import java.util.UUID;

import com.bigchatbrasil.modules.chat.entity.ChatDestinatarioEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "destinatario")
    private List<ChatDestinatarioEntity> chats;

}
