package com.soft.ob.role.service;

import com.soft.ob.role.entity.Permission;
import com.soft.ob.role.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    public Permission createPermission(Permission permission) {
        permissionMapper.insert(permission);
        return permission;
    }

    public Permission getPermissionById(Long id) {
        return permissionMapper.selectById(id);
    }

    public Permission getPermissionByCode(String code) {
        return permissionMapper.selectByCode(code);
    }

    public List<Permission> getAllPermissions() {
        return permissionMapper.selectAll();
    }

    public List<Permission> getPermissionsByModule(String module) {
        return permissionMapper.selectByModule(module);
    }

    public Permission updatePermission(Permission permission) {
        permissionMapper.update(permission);
        return permission;
    }

    public boolean deletePermission(Long id) {
        return permissionMapper.deleteById(id) > 0;
    }
}
