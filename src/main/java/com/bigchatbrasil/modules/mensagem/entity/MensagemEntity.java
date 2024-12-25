package com.bigchatbrasil.modules.mensagem.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "mensagem")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class MensagemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(length = 11, name = "numero_telefone")
    @Size(min = 11, max = 11, message = "O campo telefone deve conter 11 caracteres")
    private String numeroTelefone;

    @NotNull
    private boolean whatsapp;

    @NotNull
    private String texto;

    @Column(name = "data_hora_envio")
    @CreationTimestamp
    private LocalDateTime dataHoraEnvio;

}
