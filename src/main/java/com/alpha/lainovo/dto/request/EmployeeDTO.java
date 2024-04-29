package com.alpha.lainovo.dto.request;
public record EmployeeDTO (String accountName,
                           String fullName,
                           String password,
                           String phone,
                           String address,
                           boolean isBlocked) {

}
