package com.alpha.lainovo.service.ServiceInterface;

import com.alpha.lainovo.model.Customer;

public interface CustomerInterface {
    Customer findByEmail(String email);

    Customer findById(Integer userId);

    Customer findByRefreshToken(String token);
}
