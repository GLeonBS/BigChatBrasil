package com.bigchatbrasil.modules.destinatario.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigchatbrasil.modules.destinatario.entity.DestinatarioEntity;

public interface DestinatarioRepository extends JpaRepository<DestinatarioEntity, UUID> {
}
