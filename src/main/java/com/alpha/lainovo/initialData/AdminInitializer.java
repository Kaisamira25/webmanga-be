package com.alpha.lainovo.initialData;

import com.alpha.lainovo.model.Admin;
import com.alpha.lainovo.model.Role;
import com.alpha.lainovo.repository.AdminRepository;
import com.alpha.lainovo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@DependsOn("roleInitializer")
public class AdminInitializer implements ApplicationRunner {
    private final AdminRepository adminRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (adminRepository.count() == 0) {
            Admin adminAccount = new Admin();
            adminAccount.setAccountName("admin");
            adminAccount.setPassword(encoder.encode("admin2511"));
            adminAccount.setFullName("Admin");
            adminAccount.setPhone("0988681424");
            adminAccount.setAddress("Admin");
            Role role = roleRepository.findByRoleName("ADMIN");
            if (role != null) {
                adminAccount.setRole(role);
            } else {
                log.error("Not found role: {}",role);
            }
            adminRepository.save(adminAccount);
        }
    }
}
