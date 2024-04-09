package com.alpha.lainovo.dto.request;

public record RAdminRoleDTO (
    String accountName,
    String fullName,
    String phone,
    boolean isBlocked,
    String address,
    String roleName
) {

}
