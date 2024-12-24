package com.backoffice.modules.cliente.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backoffice.modules.cliente.entity.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {
}
