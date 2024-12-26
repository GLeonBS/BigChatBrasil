package com.bigchatbrasil.modules.chat.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "remetente_id")
    private ClienteEntity remetente;

    @OneToMany(mappedBy = "chat")
    private List<ChatDestinatarioEntity> destinatarios = new ArrayList<>();

    @OneToMany(mappedBy = "chat")
    private List<MensagemEntity> mensagens = new ArrayList<>();
}
