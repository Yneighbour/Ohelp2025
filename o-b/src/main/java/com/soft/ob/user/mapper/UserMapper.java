package com.soft.ob.user.mapper;

import com.soft.ob.user.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user (name, email, phone, role, avatar_url, is_active, created_at, updated_at) VALUES (#{name}, #{email}, #{phone}, #{role}, #{avatarUrl}, #{isActive}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectById(Long id);

    @Select("SELECT * FROM user WHERE email = #{email}")
    User selectByEmail(String email);

    @Select("SELECT * FROM user WHERE phone = #{phone}")
    User selectByPhone(String phone);

    @Select("SELECT * FROM user")
    List<User> selectAll();

    @Update("UPDATE user SET name = #{name}, email = #{email}, phone = #{phone}, role = #{role}, avatar_url = #{avatarUrl}, is_active = #{isActive}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteById(Long id);
}
