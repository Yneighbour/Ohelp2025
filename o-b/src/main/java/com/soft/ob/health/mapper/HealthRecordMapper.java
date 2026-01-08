package com.soft.ob.health.mapper;

import com.soft.ob.health.entity.HealthRecord;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface HealthRecordMapper {
    @Insert("INSERT INTO health_record (elderly_id, record_date, blood_pressure, heart_rate, temperature, weight, glucose_level, notes, doctor_id, is_active, created_at, updated_at) VALUES (#{elderlyId}, #{recordDate}, #{bloodPressure}, #{heartRate}, #{temperature}, #{weight}, #{glucoseLevel}, #{notes}, #{doctorId}, #{isActive}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(HealthRecord healthRecord);

    @Select("SELECT * FROM health_record WHERE id = #{id}")
    HealthRecord selectById(Long id);

    @Select("SELECT * FROM health_record")
    List<HealthRecord> selectAll();

    @Select("SELECT * FROM health_record WHERE elderly_id = #{elderlyId}")
    List<HealthRecord> selectByElderlyId(Long elderlyId);

    @Select("SELECT * FROM health_record WHERE record_date = #{recordDate}")
    List<HealthRecord> selectByRecordDate(LocalDate recordDate);

    @Select("SELECT * FROM health_record WHERE doctor_id = #{doctorId}")
    List<HealthRecord> selectByDoctorId(Long doctorId);

    @Update("UPDATE health_record SET elderly_id = #{elderlyId}, record_date = #{recordDate}, blood_pressure = #{bloodPressure}, heart_rate = #{heartRate}, temperature = #{temperature}, weight = #{weight}, glucose_level = #{glucoseLevel}, notes = #{notes}, doctor_id = #{doctorId}, is_active = #{isActive}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(HealthRecord healthRecord);

    @Delete("DELETE FROM health_record WHERE id = #{id}")
    int deleteById(Long id);
}
