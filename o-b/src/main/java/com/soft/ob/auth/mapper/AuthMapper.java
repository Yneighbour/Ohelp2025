package com.soft.ob.auth.mapper;

import com.soft.ob.auth.entity.Auth;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AuthMapper {
    @Insert("INSERT INTO auth (username, password, token, user_id, login_time, is_active, created_at, updated_at) VALUES (#{username}, #{password}, #{token}, #{userId}, #{loginTime}, #{isActive}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Auth auth);

    @Select("SELECT * FROM auth WHERE id = #{id}")
    Auth selectById(Long id);

    @Select("SELECT * FROM auth WHERE username = #{username}")
    Auth selectByUsername(String username);

    @Select("SELECT * FROM auth WHERE token = #{token}")
    Auth selectByToken(String token);

    @Select("SELECT * FROM auth")
    List<Auth> selectAll();

    @Update("UPDATE auth SET password = #{password}, token = #{token}, login_time = #{loginTime}, logout_time = #{logoutTime}, is_active = #{isActive}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(Auth auth);

    @Delete("DELETE FROM auth WHERE id = #{id}")
    int deleteById(Long id);
}
