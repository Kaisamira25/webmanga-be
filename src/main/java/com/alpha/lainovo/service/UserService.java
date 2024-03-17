package com.alpha.lainovo.service;

import com.alpha.lainovo.model.User;
import com.alpha.lainovo.repository.UserRepository;
import com.alpha.lainovo.service.ServiceInterface.CreateAndUpdateInterface;
import com.alpha.lainovo.service.ServiceInterface.UserInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserInterface,CreateAndUpdateInterface<Integer, User> {
    private final UserRepository userRepository;
    @Override
    public Object create(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public User update(Integer key, User entity) {
        return userRepository.save(entity);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.get();
    }
}
