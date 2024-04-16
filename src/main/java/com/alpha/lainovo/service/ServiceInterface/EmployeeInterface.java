package com.alpha.lainovo.service.ServiceInterface;


import com.alpha.lainovo.dto.request.EmployeeDTO;
import com.alpha.lainovo.dto.request.RAdminRoleDTO;
import com.alpha.lainovo.dto.request.REmployeeUpdateAndDeleteDTO;
import com.alpha.lainovo.model.Admin;

import java.util.List;

public interface EmployeeInterface {
    void createEmployee(Admin admin);
    void updateEmployee(Integer id,Admin admin);

    Admin updateEmployeeAccountName(String accountName, REmployeeUpdateAndDeleteDTO rEmployeeDTO);
    void deleteEmployeeByAccountName(String accountName);
    List<RAdminRoleDTO> getAllAccount();
    List<RAdminRoleDTO> getAccountByAccountName(String accountName);


    Admin findAdminAccountByAccountName(String accountName);
}
