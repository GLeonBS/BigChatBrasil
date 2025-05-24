package com.bigchatbrasil.modules.cliente.security;

import com.bigchatbrasil.config.security.SecurityConfig;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.cliente.useCases.ClienteTokenUseCase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class ClienteAuthenticationFilter extends OncePerRequestFilter {

    private ClienteTokenUseCase clienteTokenUseCase;

    private ClienteRepository clienteRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI().replace("/api", "");

        if (checkIfEndpointIsNotPublic(requestURI) && checkIfIsInClientePermissions(requestURI)) {
            String token = recoveryToken(request);
            if (token != null) {
                String subject = clienteTokenUseCase.getSubjectFromToken(token);
                ClienteEntity cliente = clienteRepository.findByDocumento(subject).get();
                UserDetailsImpl userDetails = new UserDetailsImpl(cliente);

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(), null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new RuntimeException("O token está ausente.");
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    private boolean checkIfIsInClientePermissions(String requestURI) {
        return Arrays.stream(SecurityConfig.CLIENTE_PERMISSIONS)
                .anyMatch(requestURI::startsWith);
    }

    private boolean checkIfEndpointIsNotPublic(String requestURI) {
        return !Arrays.asList(SecurityConfig.PERMIT_ALL_LIST).contains(requestURI);
    }
}
