package com.bigchatbrasil.modules.chat.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigchatbrasil.modules.chat.entity.ChatDestinatarioEntity;

public interface ChatDestinatarioRepository extends JpaRepository<ChatDestinatarioEntity, UUID> {
}
