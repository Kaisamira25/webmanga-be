package com.alpha.lainovo.repository;

import com.alpha.lainovo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String roleName);
    Role findByRoleId(Integer roleId);
}
