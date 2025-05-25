package com.bigchatbrasil.modules.chat.entity;

import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chat")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class ChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "remetente_id")
    private ClienteEntity remetente;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private DestinatarioEntity destinatario;

    @JsonIgnore
    @OneToMany(mappedBy = "chat")
    @Builder.Default
    private List<MensagemEntity> mensagens = new ArrayList<>();
}
