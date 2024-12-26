package com.bigchatbrasil.modules.cliente.controller;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bigchatbrasil.modules.cliente.dto.CreateClienteRequestDTO;
import com.bigchatbrasil.modules.cliente.dto.UpdateClienteRequestDTO;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.useCases.CreateClienteUseCase;
import com.bigchatbrasil.modules.cliente.useCases.FindClienteUseCase;
import com.bigchatbrasil.modules.cliente.useCases.ReadSaldoClienteUseCase;
import com.bigchatbrasil.modules.cliente.useCases.UpdateClienteUseCase;
import com.bigchatbrasil.modules.cliente.useCases.UpdateCreditoUseCase;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/cliente")
@AllArgsConstructor
public class ClienteController {

    private CreateClienteUseCase createClienteUseCase;
    private FindClienteUseCase findClienteUseCase;
    private ReadSaldoClienteUseCase readSaldoClienteUseCase;
    private UpdateClienteUseCase updateClienteUseCase;
    private UpdateCreditoUseCase updateCreditoUseCase;

    @PostMapping("/")
    public ResponseEntity<ClienteEntity> createCliente(@RequestBody CreateClienteRequestDTO cliente) {
        return ResponseEntity.ok(this.createClienteUseCase.execute(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteEntity> readCliente(@PathVariable UUID id) {
        return ResponseEntity.ok(this.findClienteUseCase.execute(id));
    }

    @GetMapping("/saldo/{id}")
    public ResponseEntity<BigDecimal> readSaldoCliente(@PathVariable UUID id) {
        return ResponseEntity.ok(this.readSaldoClienteUseCase.execute(id));
    }

    @PutMapping("/")
    public ResponseEntity<ClienteEntity> updateCliente(@RequestBody UpdateClienteRequestDTO cliente) {
        return ResponseEntity.ok(this.updateClienteUseCase.execute(cliente));
    }

    @PostMapping("/add-saldo-credito/{id}")
    public ResponseEntity<BigDecimal> addSaldoCreditoCliente(@PathVariable UUID id,
            @RequestBody BigDecimal saldo) {
        return ResponseEntity.ok(this.updateCreditoUseCase.execute(id, saldo));
    }
}
