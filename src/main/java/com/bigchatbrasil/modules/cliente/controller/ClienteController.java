package com.bigchatbrasil.modules.cliente.controller;

import com.bigchatbrasil.config.security.dto.RecoveryJwtTokenDto;
import com.bigchatbrasil.modules.cliente.dto.ClienteRequestDTO;
import com.bigchatbrasil.modules.cliente.dto.ClienteResponseDTO;
import com.bigchatbrasil.modules.cliente.dto.LoginClienteDTO;
import com.bigchatbrasil.modules.cliente.useCases.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
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
    private LoginClienteUseCase loginClienteUseCase;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> createCliente(@RequestBody ClienteRequestDTO cliente) {
        return ResponseEntity.ok(this.createClienteUseCase.execute(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> findCliente(@PathVariable UUID id) {
        return ResponseEntity.ok(this.findClienteUseCase.executeResponse(id));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> findAllClients() {
        return ResponseEntity.ok(this.findAllClientsUseCase.execute());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> updateCliente(@PathVariable UUID id, @RequestBody ClienteRequestDTO cliente) {
        return ResponseEntity.ok(this.updateClienteUseCase.execute(id, cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable UUID id) {
        deleteClienteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> loginCliente(@RequestBody LoginClienteDTO cliente) {
        try {
            RecoveryJwtTokenDto token = loginClienteUseCase.execute(cliente);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
