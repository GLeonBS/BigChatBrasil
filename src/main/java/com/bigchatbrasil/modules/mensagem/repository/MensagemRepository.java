package com.bigchatbrasil.modules.mensagem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigchatbrasil.modules.mensagem.entity.MensagemEntity;

public interface MensagemRepository extends JpaRepository<MensagemEntity, UUID> {
}
