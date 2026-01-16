package com.soft.ob.identity.mapper;

import com.soft.ob.identity.entity.UserElderMapping;
import org.apache.ibatis.annotations.*;
import java.util.Optional;
import java.util.List;

/**
 * UserElderMapping Mapper - 老人账号映射
 */
@Mapper
public interface UserElderMappingMapper {
    
    /**
     * 根据 user_id 查询映射记录
     */
    @Select("SELECT * FROM user_elder_mapping WHERE user_id = #{userId} LIMIT 1")
    Optional<UserElderMapping> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 根据 elderly_id 查询映射记录
     */
    @Select("SELECT * FROM user_elder_mapping WHERE elderly_id = #{elderlyId} LIMIT 1")
    Optional<UserElderMapping> selectByElderlyId(@Param("elderlyId") Long elderlyId);
    
    /**
     * 查询所有映射记录
     */
    @Select("SELECT * FROM user_elder_mapping ORDER BY created_at DESC")
    List<UserElderMapping> selectAll();
    
    /**
     * 创建映射记录
     */
    @Insert("INSERT INTO user_elder_mapping (user_id, elderly_id, account_source, created_at, updated_at) " +
            "VALUES (#{userId}, #{elderlyId}, #{accountSource}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(UserElderMapping mapping);
    
    /**
     * 更新映射记录
     */
    @Update("UPDATE user_elder_mapping SET account_source = #{accountSource}, updated_at = NOW() " +
            "WHERE id = #{id}")
    int update(UserElderMapping mapping);
    
    /**
     * 根据 user_id 删除映射记录
     */
    @Delete("DELETE FROM user_elder_mapping WHERE user_id = #{userId}")
    int deleteByUserId(@Param("userId") Long userId);
    
    /**
     * 根据 elderly_id 删除映射记录
     */
    @Delete("DELETE FROM user_elder_mapping WHERE elderly_id = #{elderlyId}")
    int deleteByElderlyId(@Param("elderlyId") Long elderlyId);
}
