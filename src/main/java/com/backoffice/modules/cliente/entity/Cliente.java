package com.backoffice.modules.cliente.entity;

import java.util.UUID;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String nome;

    @Email
    @NotNull
    private String email;

    @Column(length = 14)
    @Size(max = 14)
    private String telefone;

    @Column(length = 11, name = "cpf")
    @Size(min = 11, max = 11)
    @CPF
    private String cpfResponsavel;

    @Column(length = 14)
    @Size(min = 14, max = 14)
    @CNPJ
    private String cnpj;

    @Column(name = "nome_empresa")
    private String nomeEmpresa;

    @Embedded
    private Conta conta;

}
