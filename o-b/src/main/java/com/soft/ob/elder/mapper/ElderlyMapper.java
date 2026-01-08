package com.soft.ob.elder.mapper;

import com.soft.ob.elder.entity.Elderly;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ElderlyMapper {
    @Insert("INSERT INTO elderly (name, age, date_of_birth, gender, phone_number, health_status, medical_history, address, contact_person, contact_phone, is_active, created_at, updated_at) VALUES (#{name}, #{age}, #{dateOfBirth}, #{gender}, #{phoneNumber}, #{healthStatus}, #{medicalHistory}, #{address}, #{contactPerson}, #{contactPhone}, #{isActive}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Elderly elderly);

    @Select("SELECT * FROM elderly WHERE id = #{id}")
    Elderly selectById(Long id);

    @Select("SELECT * FROM elderly")
    List<Elderly> selectAll();

    @Select("SELECT * FROM elderly WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Elderly> selectByName(String name);

    @Update("UPDATE elderly SET name = #{name}, age = #{age}, date_of_birth = #{dateOfBirth}, gender = #{gender}, phone_number = #{phoneNumber}, health_status = #{healthStatus}, medical_history = #{medicalHistory}, address = #{address}, contact_person = #{contactPerson}, contact_phone = #{contactPhone}, is_active = #{isActive}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(Elderly elderly);

    @Delete("DELETE FROM elderly WHERE id = #{id}")
    int deleteById(Long id);
}
