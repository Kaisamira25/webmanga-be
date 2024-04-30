package com.alpha.lainovo.repository;

import com.alpha.lainovo.dto.request.RAdminRoleDTO;
import com.alpha.lainovo.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findAdminByAccountName (String accountName);

    @Query("SELECT new com.alpha.lainovo.dto.request.RAdminRoleDTO" +
            "(a.accountName, a.fullName, a.phone, a.isBlocked, a.address, r.roleName) " +
            "FROM Admin a JOIN a.role r")
    List<RAdminRoleDTO> getAllAccountWithRoles ();

    @Query("SELECT new com.alpha.lainovo.dto.request.RAdminRoleDTO" +
            "(a.accountName, a.fullName, a.phone, a.isBlocked, a.address, r.roleName) " +
            "FROM Admin a JOIN a.role r WHERE a.accountName LIKE %:accountName%")
    List<RAdminRoleDTO> getAccountByAccountName (String accountName);
}
