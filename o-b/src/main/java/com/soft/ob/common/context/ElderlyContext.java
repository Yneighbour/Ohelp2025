package com.soft.ob.common.context;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 身份上下文：用 ThreadLocal 存储当前请求的用户身份信息
 */
public class ElderlyContext {
    
    private static final ThreadLocal<ElderlyInfo> CONTEXT = new ThreadLocal<>();
    
    @Data
    @AllArgsConstructor
    public static class ElderlyInfo {
        private Long userId;
        private Long elderlyId;
        private String userType;      // "elder", "family", "admin", "operator", etc.
        private Long relativeId;      // 仅当 userType="family" 时有值
    }
    
    /**
     * 设置当前上下文
     */
    public static void set(ElderlyInfo info) {
        CONTEXT.set(info);
    }
    
    /**
     * 获取当前上下文
     */
    public static ElderlyInfo get() {
        return CONTEXT.get();
    }
    
    /**
     * 获取当前用户ID
     */
    public static Long getCurrentUserId() {
        ElderlyInfo info = CONTEXT.get();
        return info != null ? info.userId : null;
    }
    
    /**
     * 获取当前老人ID（老人或家属代理的老人）
     */
    public static Long getCurrentElderlyId() {
        ElderlyInfo info = CONTEXT.get();
        return info != null ? info.elderlyId : null;
    }
    
    /**
     * 获取当前用户类型
     */
    public static String getCurrentUserType() {
        ElderlyInfo info = CONTEXT.get();
        return info != null ? info.userType : null;
    }
    
    /**
     * 获取当前亲属ID（仅 family 用户有值）
     */
    public static Long getCurrentRelativeId() {
        ElderlyInfo info = CONTEXT.get();
        return info != null ? info.relativeId : null;
    }
    
    /**
     * 判断是否为老人端用户
     */
    public static boolean isElderUser() {
        return "elder".equals(getCurrentUserType());
    }
    
    /**
     * 判断是否为家属端用户
     */
    public static boolean isFamilyUser() {
        return "family".equals(getCurrentUserType());
    }
    
    /**
     * 判断是否为管理端用户
     */
    public static boolean isAdminUser() {
        String userType = getCurrentUserType();
        return "admin".equals(userType) || "operator".equals(userType) || "manager".equals(userType);
    }
    
    /**
     * 清理上下文
     */
    public static void clear() {
        CONTEXT.remove();
    }
}
