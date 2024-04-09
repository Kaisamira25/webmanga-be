package com.alpha.lainovo.initialData;

import com.alpha.lainovo.model.Admin;
import com.alpha.lainovo.model.Role;
import com.alpha.lainovo.repository.AdminRepository;
import com.alpha.lainovo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder encoder;
    @Transactional
    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role roleAdmin = new Role();
            roleAdmin.setRoleName("ADMIN");
            roleRepository.save(roleAdmin);

            Role roleEmployee = new Role();
            roleEmployee.setRoleName("EMPLOYEE");
            roleRepository.save(roleEmployee);

            Admin admin = new Admin();
            admin.setFullName("Admin");
            admin.setRole(roleAdmin);
            admin.setPassword(encoder.encode("admin12345"));
            admin.setPhone("0988681424");
            admin.setAddress("8/4C1");
            admin.setAccountName("admin");
            adminRepository.save(admin);
        } else {
            log.error("Fail to initializer data for role table");
        }
    }
}
