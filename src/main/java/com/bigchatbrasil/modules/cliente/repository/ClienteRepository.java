package com.bigchatbrasil.modules.cliente.repository;

import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {

    Optional<ClienteEntity> findByDocumento(String documento);

}
