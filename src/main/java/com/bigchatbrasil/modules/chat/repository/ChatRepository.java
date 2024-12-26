package com.bigchatbrasil.modules.chat.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigchatbrasil.modules.chat.entity.ChatEntity;

public interface ChatRepository extends JpaRepository<ChatEntity, UUID> {
}
