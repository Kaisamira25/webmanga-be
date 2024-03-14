package com.alpha.lainovo.service.ServiceInterface;

import com.alpha.lainovo.model.User;

public interface UserInterface {
    User findByEmail(String email);

    User findById(Integer userId);
}
