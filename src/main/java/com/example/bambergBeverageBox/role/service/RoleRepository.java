package com.example.bambergBeverageBox.role.service;

import com.example.bambergBeverageBox.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository   extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
