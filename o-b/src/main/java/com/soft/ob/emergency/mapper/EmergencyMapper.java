package com.soft.ob.emergency.mapper;

import com.soft.ob.emergency.entity.Emergency;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EmergencyMapper {
    @Insert("INSERT INTO emergency_request (elderly_id, type, description, location, contact_phone, status, responder_id, response_time, resolved_time, priority, is_active, created_at, updated_at) VALUES (#{elderlyId}, #{type}, #{description}, #{location}, #{contactPhone}, #{status}, #{responderId}, #{responseTime}, #{resolvedTime}, #{priority}, #{isActive}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Emergency emergency);

    @Select("SELECT * FROM emergency_request WHERE id = #{id}")
    Emergency selectById(Long id);

    @Select("SELECT * FROM emergency_request")
    List<Emergency> selectAll();

    @Select("SELECT * FROM emergency_request WHERE elderly_id = #{elderlyId}")
    List<Emergency> selectByElderlyId(Long elderlyId);

    @Select("SELECT * FROM emergency_request WHERE status = #{status}")
    List<Emergency> selectByStatus(String status);

    @Select("SELECT * FROM emergency_request WHERE priority = #{priority}")
    List<Emergency> selectByPriority(String priority);

    @Update("UPDATE emergency_request SET elderly_id = #{elderlyId}, type = #{type}, description = #{description}, location = #{location}, contact_phone = #{contactPhone}, status = #{status}, responder_id = #{responderId}, response_time = #{responseTime}, resolved_time = #{resolvedTime}, priority = #{priority}, is_active = #{isActive}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(Emergency emergency);

    @Delete("DELETE FROM emergency_request WHERE id = #{id}")
    int deleteById(Long id);
}
