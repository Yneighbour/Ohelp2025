package com.soft.ob.identity.mapper;

import com.soft.ob.identity.entity.UserFamilyMapping;
import org.apache.ibatis.annotations.*;
import java.util.Optional;
import java.util.List;

/**
 * UserFamilyMapping Mapper - 家属账号映射
 */
@Mapper
public interface UserFamilyMappingMapper {
    
    /**
     * 根据 user_id 查询映射记录
     */
    @Select("SELECT * FROM user_family_mapping WHERE user_id = #{userId} LIMIT 1")
    Optional<UserFamilyMapping> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 根据 relative_id 查询映射记录
     */
    @Select("SELECT * FROM user_family_mapping WHERE relative_id = #{relativeId} LIMIT 1")
    Optional<UserFamilyMapping> selectByRelativeId(@Param("relativeId") Long relativeId);
    
    /**
     * 根据 elderly_id 查询所有映射记录（查某个老人的所有家属账号）
     */
    @Select("SELECT * FROM user_family_mapping WHERE elderly_id = #{elderlyId} ORDER BY created_at DESC")
    List<UserFamilyMapping> selectByElderlyId(@Param("elderlyId") Long elderlyId);
    
    /**
     * 查询所有映射记录
     */
    @Select("SELECT * FROM user_family_mapping ORDER BY created_at DESC")
    List<UserFamilyMapping> selectAll();
    
    /**
     * 创建映射记录
     */
    @Insert("INSERT INTO user_family_mapping (user_id, elderly_id, relative_id, is_primary_contact, account_source, created_at, updated_at) " +
            "VALUES (#{userId}, #{elderlyId}, #{relativeId}, #{isPrimaryContact}, #{accountSource}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(UserFamilyMapping mapping);
    
    /**
     * 更新映射记录
     */
    @Update("UPDATE user_family_mapping SET is_primary_contact = #{isPrimaryContact}, account_source = #{accountSource}, updated_at = NOW() " +
            "WHERE id = #{id}")
    int update(UserFamilyMapping mapping);
    
    /**
     * 根据 user_id 删除映射记录
     */
    @Delete("DELETE FROM user_family_mapping WHERE user_id = #{userId}")
    int deleteByUserId(@Param("userId") Long userId);
    
    /**
     * 根据 relative_id 删除映射记录
     */
    @Delete("DELETE FROM user_family_mapping WHERE relative_id = #{relativeId}")
    int deleteByRelativeId(@Param("relativeId") Long relativeId);
    
    /**
     * 根据 elderly_id 删除所有映射记录
     */
    @Delete("DELETE FROM user_family_mapping WHERE elderly_id = #{elderlyId}")
    int deleteByElderlyId(@Param("elderlyId") Long elderlyId);
}
