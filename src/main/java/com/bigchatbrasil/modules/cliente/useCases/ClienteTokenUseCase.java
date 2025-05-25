package com.bigchatbrasil.modules.cliente.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bigchatbrasil.config.security.dto.RecoveryJwtTokenDto;
import com.bigchatbrasil.modules.cliente.config.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ClienteTokenUseCase {
    @Value("${security.token.secret.cliente}")
    private String secretKey;

    private static final String ISSUER = "big-chat-brasil";

    public RecoveryJwtTokenDto generateToken(UserDetailsImpl user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            ZonedDateTime time = ZonedDateTime.now();

            Instant expiresAt = time.plusHours(4).toInstant();

            String token = JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(time.toInstant())
                    .withExpiresAt(expiresAt)
                    .withSubject(user.getUsername())
                    .sign(algorithm);

            List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            return new RecoveryJwtTokenDto(token, expiresAt.toEpochMilli(), roles);

        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token inválido ou expirado.");
        }
    }
}
