package com.bigchatbrasil.modules.chat.repository;

import com.bigchatbrasil.modules.chat.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<ChatEntity, UUID> {

    List<ChatEntity> findAllByIdIn(List<UUID> ids);

    List<ChatEntity> findAllByRemetenteId(UUID id);
}
