package com.alpha.lainovo.dto.request;

public record REmployeeUpdateAndDeleteDTO(String fullName,
                                          boolean isBlocked,
                                          String phoneNumber,
                                          String address) {
}
