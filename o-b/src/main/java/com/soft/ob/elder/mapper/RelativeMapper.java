package com.soft.ob.elder.mapper;

import com.soft.ob.elder.entity.Relative;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RelativeMapper {
    @Insert("INSERT INTO relative (elderly_id, name, phone, relationship, email, is_primary_contact, is_active, created_at, updated_at) VALUES (#{elderlyId}, #{name}, #{phone}, #{relationship}, #{email}, #{isPrimaryContact}, #{isActive}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Relative relative);

    @Select("SELECT * FROM relative WHERE id = #{id}")
    Relative selectById(Long id);

    @Select("SELECT * FROM relative WHERE elderly_id = #{elderlyId}")
    List<Relative> selectByElderlyId(Long elderlyId);

    @Select("SELECT * FROM relative")
    List<Relative> selectAll();

    @Update("UPDATE relative SET elderly_id = #{elderlyId}, name = #{name}, phone = #{phone}, relationship = #{relationship}, email = #{email}, is_primary_contact = #{isPrimaryContact}, is_active = #{isActive}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(Relative relative);

    @Delete("DELETE FROM relative WHERE id = #{id}")
    int deleteById(Long id);
}
