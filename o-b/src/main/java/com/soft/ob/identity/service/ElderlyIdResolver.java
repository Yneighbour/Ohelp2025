package com.soft.ob.identity.service;

import com.soft.ob.identity.entity.UserElderMapping;
import com.soft.ob.identity.entity.UserFamilyMapping;
import com.soft.ob.identity.mapper.UserElderMappingMapper;
import com.soft.ob.identity.mapper.UserFamilyMappingMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * 身份解析服务：根据 user_id 解析用户绑定的老人ID与用户类型
 */
@Service
public class ElderlyIdResolver {
    
    @Autowired
    private UserElderMappingMapper userElderMappingMapper;
    
    @Autowired
    private UserFamilyMappingMapper userFamilyMappingMapper;
    
    @Data
    @AllArgsConstructor
    public static class ElderlyBinding {
        private Long elderlyId;
        private String userType;      // "elder" or "family"
        private Long relativeId;      // 仅当 userType="family" 时有值
    }
    
    /**
     * 根据 user_id 解析该用户绑定的老人ID
     * 优先查询 elder 映射，然后查询 family 映射
     * 
     * @param userId 登录用户ID
     * @return 包含 elderlyId、userType、relativeId 的结果对象
     */
    public Optional<ElderlyBinding> resolveElderlyId(Long userId) {
        if (userId == null) {
            return Optional.empty();
        }
        
        // 先查 elder 映射
        Optional<UserElderMapping> elderMapping = userElderMappingMapper.selectByUserId(userId);
        if (elderMapping.isPresent()) {
            UserElderMapping mapping = elderMapping.get();
            return Optional.of(new ElderlyBinding(
                mapping.getElderlyId(),
                "elder",
                null
            ));
        }
        
        // 再查 family 映射
        Optional<UserFamilyMapping> familyMapping = userFamilyMappingMapper.selectByUserId(userId);
        if (familyMapping.isPresent()) {
            UserFamilyMapping mapping = familyMapping.get();
            return Optional.of(new ElderlyBinding(
                mapping.getElderlyId(),
                "family",
                mapping.getRelativeId()
            ));
        }
        
        return Optional.empty();
    }
    
    /**
     * 根据 elderly_id 查询对应的 elder 账号 user_id
     * 
     * @param elderlyId 老人ID
     * @return 对应的 user_id（elder 账号）
     */
    public Optional<Long> resolveElderUserId(Long elderlyId) {
        if (elderlyId == null) {
            return Optional.empty();
        }
        
        return userElderMappingMapper.selectByElderlyId(elderlyId)
            .map(UserElderMapping::getUserId);
    }
    
    /**
     * 创建或更新老人账号映射
     */
    public void createElderMapping(Long userId, Long elderlyId, String accountSource) {
        UserElderMapping mapping = new UserElderMapping();
        mapping.setUserId(userId);
        mapping.setElderlyId(elderlyId);
        mapping.setAccountSource(accountSource != null ? accountSource : "manual");
        
        userElderMappingMapper.insert(mapping);
    }
    
    /**
     * 创建或更新家属账号映射
     */
    public void createFamilyMapping(Long userId, Long elderlyId, Long relativeId, 
                                   Boolean isPrimaryContact, String accountSource) {
        UserFamilyMapping mapping = new UserFamilyMapping();
        mapping.setUserId(userId);
        mapping.setElderlyId(elderlyId);
        mapping.setRelativeId(relativeId);
        mapping.setIsPrimaryContact(isPrimaryContact != null ? isPrimaryContact : false);
        mapping.setAccountSource(accountSource != null ? accountSource : "manual");
        
        userFamilyMappingMapper.insert(mapping);
    }
    
    /**
     * 删除用户的身份映射
     */
    public void deleteUserMapping(Long userId) {
        userElderMappingMapper.deleteByUserId(userId);
        userFamilyMappingMapper.deleteByUserId(userId);
    }
    
    /**
     * 删除老人的所有身份映射
     */
    public void deleteElderlyMappings(Long elderlyId) {
        userElderMappingMapper.deleteByElderlyId(elderlyId);
        userFamilyMappingMapper.deleteByElderlyId(elderlyId);
    }
}
