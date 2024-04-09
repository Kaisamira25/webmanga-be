package com.alpha.lainovo.repository;

import com.alpha.lainovo.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findAdminByAccountName (String accountName);
}
