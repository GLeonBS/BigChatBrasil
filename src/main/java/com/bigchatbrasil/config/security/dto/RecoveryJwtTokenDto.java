package com.bigchatbrasil.config.security.dto;

import java.util.List;

public record RecoveryJwtTokenDto(String token, Long expiresIn, List<String> roles) {
}
