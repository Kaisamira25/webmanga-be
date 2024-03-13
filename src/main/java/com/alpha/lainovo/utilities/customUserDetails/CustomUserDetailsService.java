package com.alpha.lainovo.utilities.customUserDetails;

import com.alpha.lainovo.model.User;
import com.alpha.lainovo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Tìm user bằng email
        User user = userRepository.findByEmail(email);
        if (user != null) { // check có data không
            return new CustomUserDetails(user); // trả về một instance của CustomUserDetails
        }
        log.error("CustomUserDetailsService: loadUserByUsername | User not found with the given email");
        throw new UsernameNotFoundException("User not found with email: " + email);

    }
}
