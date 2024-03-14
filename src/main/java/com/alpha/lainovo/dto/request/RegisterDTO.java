package com.alpha.lainovo.dto.request;

public record RegisterDTO(
        String email,
        String fullName,
        String password
) {
}
