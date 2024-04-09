package com.alpha.lainovo.initialData;

import com.alpha.lainovo.model.Role;
import com.alpha.lainovo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class RoleInitializer implements ApplicationRunner {
    private final RoleRepository roleRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (roleRepository.count() == 0) {
            Role customerRole = new Role("CUSTOMER");
            Role adminRole = new Role("ADMIN");
            Role employee = new Role("EMPLOYEE");
            roleRepository.saveAll(Arrays.asList(customerRole,adminRole,employee));
        }
    }
}
