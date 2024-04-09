package com.alpha.lainovo.service;

import com.alpha.lainovo.dto.request.EmployeeDTO;
import com.alpha.lainovo.model.Admin;
import com.alpha.lainovo.model.Role;
import com.alpha.lainovo.repository.AdminRepository;
import com.alpha.lainovo.repository.RoleRepository;
import com.alpha.lainovo.service.ServiceInterface.CreateAndUpdateInterface;
import com.alpha.lainovo.service.ServiceInterface.EmployeeInterface;
import com.alpha.lainovo.utilities.token.GenerateToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeInterface {
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;

    @Override
    public void createEmployee(Admin admin) {
        adminRepository.save(admin);
    }

    @Override
    public void updateEmployee(Integer id, Admin admin) {
        adminRepository.save(admin);
    }

    @Override
    public Admin findAdminAccountByAccountName(String accountName) {
        return adminRepository.findAdminByAccountName(accountName);

    }

}
