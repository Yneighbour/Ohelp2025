package com.soft.ob.worker.mapper;

import com.soft.ob.worker.entity.Worker;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WorkerMapper {
    @Insert("INSERT INTO worker (name, email, phone, position, department, specialization, hire_date, salary, is_available, is_active, created_at, updated_at) VALUES (#{name}, #{email}, #{phone}, #{position}, #{department}, #{specialization}, #{hireDate}, #{salary}, #{isAvailable}, #{isActive}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Worker worker);

    @Select("SELECT * FROM worker WHERE id = #{id}")
    Worker selectById(Long id);

    @Select("SELECT * FROM worker")
    List<Worker> selectAll();

    @Select("SELECT * FROM worker WHERE email = #{email}")
    Worker selectByEmail(String email);

    @Select("SELECT * FROM worker WHERE phone = #{phone}")
    Worker selectByPhone(String phone);

    @Select("SELECT * FROM worker WHERE department = #{department}")
    List<Worker> selectByDepartment(String department);

    @Select("SELECT * FROM worker WHERE position = #{position}")
    List<Worker> selectByPosition(String position);

    @Select("SELECT * FROM worker WHERE is_available = #{isAvailable}")
    List<Worker> selectByAvailability(Boolean isAvailable);

    @Update("UPDATE worker SET name = #{name}, email = #{email}, phone = #{phone}, position = #{position}, department = #{department}, specialization = #{specialization}, hire_date = #{hireDate}, salary = #{salary}, is_available = #{isAvailable}, is_active = #{isActive}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(Worker worker);

    @Delete("DELETE FROM worker WHERE id = #{id}")
    int deleteById(Long id);
}
