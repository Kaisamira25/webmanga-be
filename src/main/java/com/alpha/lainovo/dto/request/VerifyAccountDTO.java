package com.alpha.lainovo.dto.request;

public record VerifyAccountDTO(
        String otp,
        String email
) {
}
