package com.soft.ob.role.service;

import com.soft.ob.role.entity.Permission;
import com.soft.ob.role.entity.RolePermission;
import com.soft.ob.role.mapper.RolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    public List<Permission> getPermissionsByRoleId(Long roleId) {
        return rolePermissionMapper.selectPermissionsByRoleId(roleId);
    }

    @Transactional
    public boolean assignPermissionsToRole(Long roleId, List<Long> permissionIds) {
        // 先删除该角色的所有权限
        rolePermissionMapper.deleteByRoleId(roleId);
        
        // 再添加新的权限
        if (permissionIds != null && !permissionIds.isEmpty()) {
            for (Long permissionId : permissionIds) {
                RolePermission rp = new RolePermission();
                rp.setRoleId(roleId);
                rp.setPermissionId(permissionId);
                rolePermissionMapper.insert(rp);
            }
        }
        return true;
    }

    public boolean removePermissionFromRole(Long roleId, Long permissionId) {
        return rolePermissionMapper.deleteByRoleIdAndPermissionId(roleId, permissionId) > 0;
    }
}
