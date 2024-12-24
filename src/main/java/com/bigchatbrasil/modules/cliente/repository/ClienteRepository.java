package com.bigchatbrasil.modules.cliente.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {
}
