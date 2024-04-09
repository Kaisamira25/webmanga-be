package com.alpha.lainovo.initialData;

import com.alpha.lainovo.model.Role;
import com.alpha.lainovo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class RoleInitializer implements ApplicationRunner {
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (roleRepository.count() == 0) {
            Role customerRole = new Role("CUSTOMER");
            Role adminRole = new Role("ADMIN");
            Role employeeRole = new Role("EMPLOYEE");

            roleRepository.saveAll(Arrays.asList(customerRole, adminRole, employeeRole));

            log.info("Roles 'CUSTOMER', 'ADMIN', and 'EMPLOYEE' have been created successfully.");
        } else {
            log.info("Roles already exist.");
        }
    }
}

