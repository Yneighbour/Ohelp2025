package com.soft.ob.role.service;

import com.soft.ob.role.entity.Role;
import com.soft.ob.role.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public Role createRole(Role role) {
        roleMapper.insert(role);
        return role;
    }

    public Role getRoleById(Long id) {
        return roleMapper.selectById(id);
    }

    public Role getRoleByCode(String code) {
        return roleMapper.selectByCode(code);
    }

    public List<Role> getAllRoles() {
        return roleMapper.selectAll();
    }

    public Role updateRole(Role role) {
        roleMapper.update(role);
        return role;
    }

    public boolean deleteRole(Long id) {
        return roleMapper.deleteById(id) > 0;
    }

    public boolean activateRole(Long id) {
        return roleMapper.activate(id) > 0;
    }

    public boolean deactivateRole(Long id) {
        return roleMapper.deactivate(id) > 0;
    }
}
