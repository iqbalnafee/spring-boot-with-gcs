package com.example.bambergBeverageBox.role.service;

import com.example.bambergBeverageBox.role.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findByRoleName(String roleName) {
        Role role = roleRepository.findByRoleName(roleName);
        if (role == null) return createRole(roleName);
        return role;
    }

    @Transactional
    public Role createRole(String roleAdmin) {
        Role role = new Role();
        role.setRoleName(roleAdmin);
        return roleRepository.save(role);
    }
}
