package com.alpha.lainovo.service.ServiceInterface;


import com.alpha.lainovo.dto.request.EmployeeDTO;
import com.alpha.lainovo.model.Admin;

public interface EmployeeInterface {
    void createEmployee(Admin admin);
    void updateEmployee(Integer id,Admin admin);

    Admin findAdminAccountByAccountName(String accountName);
}
