package com.soft.ob.role.mapper;

import com.soft.ob.role.entity.RolePermission;
import com.soft.ob.role.entity.Permission;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RolePermissionMapper {

    @Select("SELECT * FROM role_permission WHERE role_id = #{roleId}")
    List<RolePermission> selectByRoleId(Long roleId);

    @Select("SELECT p.* FROM permission p " +
            "INNER JOIN role_permission rp ON p.id = rp.permission_id " +
            "WHERE rp.role_id = #{roleId}")
    List<Permission> selectPermissionsByRoleId(Long roleId);

    @Insert("INSERT INTO role_permission (role_id, permission_id, created_at) " +
            "VALUES (#{roleId}, #{permissionId}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RolePermission rolePermission);

    @Delete("DELETE FROM role_permission WHERE role_id = #{roleId}")
    int deleteByRoleId(Long roleId);

    @Delete("DELETE FROM role_permission WHERE role_id = #{roleId} AND permission_id = #{permissionId}")
    int deleteByRoleIdAndPermissionId(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
}
