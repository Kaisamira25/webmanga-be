package com.alpha.lainovo.service;

import com.alpha.lainovo.dto.request.EmployeeDTO;
import com.alpha.lainovo.dto.request.PublicationsImageDTO;
import com.alpha.lainovo.dto.request.RAdminRoleDTO;
import com.alpha.lainovo.dto.request.REmployeeUpdateAndDeleteDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public List<RAdminRoleDTO> getAllAccount() {
        return adminRepository.getAllAccountWithRoles();
    }

    @Transactional
    public List<RAdminRoleDTO> getAccountByAccountName(String accountName) {
        return adminRepository.getAccountByAccountName(accountName);
    }

    @Override
    public Admin updateEmployeeAccountName(String accountName, REmployeeUpdateAndDeleteDTO rEmployeeDTO) {
        Admin admin = adminRepository.findAdminByAccountName(accountName);
        if (admin != null) {
            admin.setFullName(rEmployeeDTO.fullName());
            admin.setPhone(rEmployeeDTO.phoneNumber());
            admin.setAddress(rEmployeeDTO.address());
            admin.setBlocked(rEmployeeDTO.isBlocked());
            adminRepository.save(admin);
            log.info(">>>>>> EmployeeServiceImpl:updateAccountEmployee | Update an Employee with account name: {}", admin.getAccountName());
            return admin;
        }
        log.error(">>>>>>> EmployeeServiceImpl:updateAccountEmployee | No Employee found to update with account name: {}", accountName);
        return null;
    }

    @Override
    public void deleteEmployeeByAccountName(String accountName) {
//        Admin admin = adminRepository.findAdminByAccountName(accountName);
//        if (admin != null) {
//            // Xóa mối quan hệ giữa Admin và Role tức là xóa ở trong bảng phụ
//            for (Role role : admin.getRoles()) {
//                role.getAdmins().remove(admin);
//                roleRepository.save(role); // Lưu lại trong bảng role và cập nhật lại mối quan hệ dưới databasr
//            }
//            admin.getRoles().clear(); // Clear lại roles trong admin (tức là role của admin đó đã bị xóa)
//            adminRepository.save(admin); // Lưu lại trong bảng admin và cập nhật lại mối quan hệ dưới databasr
//            adminRepository.delete(admin); // Sau đó xóa account trong bảng admin
//            log.info(">>>>>> EmployeeServiceImpl:deleteEmployeeByAccountName | Deleted an Employee with account name: {}", admin.getAccountName());
//        } else {
//            log.error(">>>>>>> EmployeeServiceImpl:deleteEmployeeByAccountName | No Employee found to delete with account name: {}", accountName);
//        }
    }
}
