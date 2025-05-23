package com.bigchatbrasil.modules.cliente.controller;

import com.bigchatbrasil.modules.cliente.dto.ClienteRequestDTO;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.useCases.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cliente")
@AllArgsConstructor
public class ClienteController {

    private CreateClienteUseCase createClienteUseCase;
    private FindClienteUseCase findClienteUseCase;
    private ReadSaldoClienteUseCase readSaldoClienteUseCase;
    private UpdateClienteUseCase updateClienteUseCase;
    private UpdateCreditoUseCase updateCreditoUseCase;
    private FindAllClientsUseCase findAllClientsUseCase;
    private DeleteClienteUseCase deleteClienteUseCase;

    @PostMapping
    public ResponseEntity<ClienteEntity> createCliente(@RequestBody ClienteRequestDTO cliente) {
        return ResponseEntity.ok(this.createClienteUseCase.execute(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteEntity> findCliente(@PathVariable UUID id) {
        return ResponseEntity.ok(this.findClienteUseCase.execute(id));
    }

    @GetMapping
    public ResponseEntity<List<ClienteEntity>> findAllClients() {
        return ResponseEntity.ok(this.findAllClientsUseCase.execute());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteEntity> updateCliente(@PathVariable UUID id, @RequestBody ClienteRequestDTO cliente) {
        return ResponseEntity.ok(this.updateClienteUseCase.execute(id, cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable UUID id) {
        deleteClienteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/saldo/{id}")
    public ResponseEntity<BigDecimal> readSaldoCliente(@PathVariable UUID id) {
        return ResponseEntity.ok(this.readSaldoClienteUseCase.execute(id));
    }

    @PostMapping("/add-saldo-credito/{id}")
    public ResponseEntity<BigDecimal> addSaldoCreditoCliente(@PathVariable UUID id,
                                                             @RequestBody BigDecimal saldo) {
        return ResponseEntity.ok(this.updateCreditoUseCase.execute(id, saldo));
    }
}
