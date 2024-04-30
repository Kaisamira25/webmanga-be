package com.alpha.lainovo.dto.request;

public record RCustomerDTO(String fullName,
                            String email,
                           String phoneNumber,
                           String address,
                           boolean isBlocked){
}
