package com.alpha.lainovo.dto.request;

import com.alpha.lainovo.model.enums.CustomerRole;

public record RCustomerRoleDTO(
        String fullName,
        String email,
        String phoneNumber,
        String address,
        CustomerRole role
) {}


