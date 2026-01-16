package com.soft.ob.role.mapper;

import com.soft.ob.role.entity.Permission;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PermissionMapper {

    @Select("SELECT * FROM permission WHERE id = #{id}")
    Permission selectById(Long id);

    @Select("SELECT * FROM permission WHERE code = #{code}")
    Permission selectByCode(String code);

    @Select("SELECT * FROM permission ORDER BY module, id")
    List<Permission> selectAll();

    @Select("SELECT * FROM permission WHERE module = #{module} ORDER BY id")
    List<Permission> selectByModule(String module);

    @Insert("INSERT INTO permission (name, code, module, description, is_active, created_at, updated_at) " +
            "VALUES (#{name}, #{code}, #{module}, #{description}, #{isActive}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Permission permission);

    @Update("UPDATE permission SET name = #{name}, code = #{code}, module = #{module}, " +
            "description = #{description}, is_active = #{isActive}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(Permission permission);

    @Delete("DELETE FROM permission WHERE id = #{id}")
    int deleteById(Long id);
}
