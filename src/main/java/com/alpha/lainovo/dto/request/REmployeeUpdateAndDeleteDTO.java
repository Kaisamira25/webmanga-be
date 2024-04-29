package com.alpha.lainovo.dto.request;

public record REmployeeUpdateAndDeleteDTO(String fullName,
                                          boolean isBlocked,
                                          String phone,
                                          String address) {
}
