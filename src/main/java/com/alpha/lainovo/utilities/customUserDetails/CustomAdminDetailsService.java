package com.alpha.lainovo.utilities.customUserDetails;

import com.alpha.lainovo.model.Admin;
import com.alpha.lainovo.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomAdminDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;
    @Override
    public UserDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
        Admin admin = adminRepository.findAdminByAccountName(accountName);
        if (admin != null) {
            log.info("LoadUserByAccountName: Success");
            return new CustomAdminDetails(admin);
        }
        log.error("LoadUserByAccountName: Failure");
        return null;
    }
}
