package com.soft.ob.role.mapper;

import com.soft.ob.role.entity.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleMapper {

    @Select("SELECT * FROM role WHERE id = #{id}")
    Role selectById(Long id);

    @Select("SELECT * FROM role WHERE code = #{code}")
    Role selectByCode(String code);

    @Select("SELECT * FROM role ORDER BY id")
    List<Role> selectAll();

    @Insert("INSERT INTO role (name, code, description, is_active, created_at, updated_at) " +
            "VALUES (#{name}, #{code}, #{description}, #{isActive}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Role role);

    @Update("UPDATE role SET name = #{name}, code = #{code}, description = #{description}, " +
            "is_active = #{isActive}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(Role role);

    @Delete("DELETE FROM role WHERE id = #{id}")
    int deleteById(Long id);

    @Update("UPDATE role SET is_active = true, updated_at = NOW() WHERE id = #{id}")
    int activate(Long id);

    @Update("UPDATE role SET is_active = false, updated_at = NOW() WHERE id = #{id}")
    int deactivate(Long id);
}
