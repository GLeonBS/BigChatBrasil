package com.bigchatbrasil.modules.chat.entity;

import java.util.UUID;

import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "chat_destinatario")
@Data
@EqualsAndHashCode(of = "id")
public class ChatDestinatarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatEntity chat;

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private DestinatarioEntity destinatario;
}
