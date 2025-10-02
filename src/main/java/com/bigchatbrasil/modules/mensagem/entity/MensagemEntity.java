package com.bigchatbrasil.modules.mensagem.entity;

import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;
import com.bigchatbrasil.modules.mensagem.enums.PrioridadeEnum;
import com.bigchatbrasil.modules.mensagem.enums.StatusMensagem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "mensagem")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class MensagemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatEntity chat;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private DestinatarioEntity destinatario;

    @NotNull
    private String texto;

    @Column(name = "data_hora_envio")
    @CreationTimestamp
    private LocalDateTime dataHoraEnvio;

    @NotNull
    private PrioridadeEnum prioridade;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusMensagem status;

    @NotNull
    private BigDecimal custo = BigDecimal.ZERO;

    @NotNull
    private boolean whatsapp;
}
