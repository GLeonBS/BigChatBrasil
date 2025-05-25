package com.bigchatbrasil.modules.cliente.useCases;

import com.bigchatbrasil.config.security.dto.RecoveryJwtTokenDto;
import com.bigchatbrasil.exceptions.UserNotFoundException;
import com.bigchatbrasil.modules.cliente.config.security.UserDetailsImpl;
import com.bigchatbrasil.modules.cliente.dto.LoginClienteDTO;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@AllArgsConstructor
public class LoginClienteUseCase {

    private final ClienteRepository repository;
    private final ClienteTokenUseCase clienteTokenUseCase;
    private final PasswordEncoder passwordEncoder;

    public RecoveryJwtTokenDto execute(LoginClienteDTO loginClienteDTO) throws AuthenticationException {
        ClienteEntity cliente = this.repository.findByDocumento(loginClienteDTO.documento())
                .orElseThrow(UserNotFoundException::new);

        boolean passwordMatches = this.passwordEncoder.matches(loginClienteDTO.senha(), cliente.getSenha());
        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        UserDetailsImpl userDetails = new UserDetailsImpl(cliente);

        return this.clienteTokenUseCase.generateToken(userDetails);
    }
}
