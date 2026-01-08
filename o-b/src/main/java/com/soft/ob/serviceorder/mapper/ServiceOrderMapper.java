package com.soft.ob.serviceorder.mapper;

import com.soft.ob.serviceorder.entity.ServiceOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ServiceOrderMapper {
    @Insert("INSERT INTO service_order (elderly_id, service_type, service_provider_id, start_date, end_date, frequency, price, status, description, is_active, created_at, updated_at) VALUES (#{elderlyId}, #{serviceType}, #{serviceProviderId}, #{startDate}, #{endDate}, #{frequency}, #{price}, #{status}, #{description}, #{isActive}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ServiceOrder serviceOrder);

    @Select("SELECT * FROM service_order WHERE id = #{id}")
    ServiceOrder selectById(Long id);

    @Select("SELECT * FROM service_order")
    List<ServiceOrder> selectAll();

    @Select("SELECT * FROM service_order WHERE elderly_id = #{elderlyId}")
    List<ServiceOrder> selectByElderlyId(Long elderlyId);

    @Select("SELECT * FROM service_order WHERE service_type = #{serviceType}")
    List<ServiceOrder> selectByServiceType(String serviceType);

    @Select("SELECT * FROM service_order WHERE service_provider_id = #{serviceProviderId}")
    List<ServiceOrder> selectByServiceProviderId(Long serviceProviderId);

    @Select("SELECT * FROM service_order WHERE status = #{status}")
    List<ServiceOrder> selectByStatus(String status);

    @Update("UPDATE service_order SET elderly_id = #{elderlyId}, service_type = #{serviceType}, service_provider_id = #{serviceProviderId}, start_date = #{startDate}, end_date = #{endDate}, frequency = #{frequency}, price = #{price}, status = #{status}, description = #{description}, is_active = #{isActive}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(ServiceOrder serviceOrder);

    @Delete("DELETE FROM service_order WHERE id = #{id}")
    int deleteById(Long id);
}
