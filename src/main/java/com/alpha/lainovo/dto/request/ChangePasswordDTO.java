package com.alpha.lainovo.dto.request;

public record ChangePasswordDTO(
        String password,
        String newPassword,
        String confirmPassword
) {
}
