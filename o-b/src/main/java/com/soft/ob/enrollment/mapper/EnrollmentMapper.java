package com.soft.ob.enrollment.mapper;

import com.soft.ob.enrollment.entity.Enrollment;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface EnrollmentMapper {
    @Select("SELECT * FROM enrollment WHERE id = #{id}")
    Enrollment getById(Long id);

    @Select("SELECT * FROM enrollment WHERE is_active = true ORDER BY enroll_time DESC")
    List<Enrollment> listAll();

    @Select("SELECT * FROM enrollment WHERE activity_id = #{activityId} AND is_active = true ORDER BY enroll_time DESC")
    List<Enrollment> listByActivity(Long activityId);

    @Select("SELECT * FROM enrollment WHERE elderly_id = #{elderlyId} AND is_active = true ORDER BY enroll_time DESC")
    List<Enrollment> listByElderly(Long elderlyId);

    @Select("SELECT * FROM enrollment WHERE status = #{status} AND is_active = true ORDER BY enroll_time DESC")
    List<Enrollment> listByStatus(String status);

    @Insert("INSERT INTO enrollment(activity_id, elderly_id, status, enroll_time, notes, is_active, created_at, updated_at) " +
            "VALUES(#{activityId}, #{elderlyId}, #{status}, #{enrollTime}, #{notes}, #{isActive}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int create(Enrollment enrollment);

    @Update("UPDATE enrollment SET activity_id = #{activityId}, elderly_id = #{elderlyId}, status = #{status}, " +
            "enroll_time = #{enrollTime}, check_in_time = #{checkInTime}, notes = #{notes}, is_active = #{isActive}, " +
            "updated_at = #{updatedAt} WHERE id = #{id}")
    int update(Enrollment enrollment);

    @Update("UPDATE enrollment SET is_active = false, updated_at = NOW() WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE enrollment SET status = #{status}, check_in_time = #{checkInTime}, updated_at = NOW() WHERE id = #{id}")
    int updateStatus(Long id, String status, LocalDateTime checkInTime);

    @Delete("DELETE FROM enrollment WHERE id = #{id}")
    int hardDelete(Long id);
}
