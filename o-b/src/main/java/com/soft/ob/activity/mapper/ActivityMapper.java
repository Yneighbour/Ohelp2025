package com.soft.ob.activity.mapper;

import com.soft.ob.activity.entity.Activity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActivityMapper {
    @Insert("INSERT INTO activity (name, category, description, location, start_time, end_time, participants, organizer_id, status, is_active, created_at, updated_at) VALUES (#{name}, #{category}, #{description}, #{location}, #{startTime}, #{endTime}, #{participants}, #{organizerId}, #{status}, #{isActive}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Activity activity);

    @Select("SELECT * FROM activity WHERE id = #{id}")
    Activity selectById(Long id);

    @Select("SELECT * FROM activity")
    List<Activity> selectAll();

    @Select("SELECT * FROM activity WHERE category = #{category}")
    List<Activity> selectByCategory(String category);

    @Select("SELECT * FROM activity WHERE status = #{status}")
    List<Activity> selectByStatus(String status);

    @Update("UPDATE activity SET name = #{name}, category = #{category}, description = #{description}, location = #{location}, start_time = #{startTime}, end_time = #{endTime}, participants = #{participants}, organizer_id = #{organizerId}, status = #{status}, is_active = #{isActive}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(Activity activity);

    @Delete("DELETE FROM activity WHERE id = #{id}")
    int deleteById(Long id);
}
