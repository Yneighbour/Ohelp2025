package com.soft.ob.common.interceptor;

import com.soft.ob.common.context.ElderlyContext;
import com.soft.ob.identity.service.ElderlyIdResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 身份上下文拦截器：在每个请求前解析身份信息并存入 ThreadLocal
 * 
 * 流程：
 * 1. 从 Authorization header 解出 token（Bearer <token>）
 * 2. 从 token 解出 user_id
 * 3. 根据 user_id 查询绑定的 elderly_id 与 user_type
 * 4. 存入 ElderlyContext（ThreadLocal）供后续业务使用
 * 5. 请求完成后清理上下文
 */
@Component
public class ElderlyContextInterceptor implements HandlerInterceptor {
    
    @Autowired
    private ElderlyIdResolver elderlyIdResolver;
    
    /**
     * 前置处理：解析身份信息
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
            throws Exception {
        try {
            // 从 Authorization header 提取 token
            String token = extractTokenFromHeader(request);
            if (token == null) {
                // 不存在 token，可能是公开接口，允许继续
                return true;
            }
            
            // 从 token 解出 user_id（需要 JwtTokenProvider 或类似工具）
            Long userId = parseUserIdFromToken(token);
            if (userId == null) {
                // token 无效，允许继续（由后续业务判断）
                return true;
            }
            
            // 根据 user_id 解析 elderly_id 与 user_type
            Optional<ElderlyIdResolver.ElderlyBinding> binding = elderlyIdResolver.resolveElderlyId(userId);
            if (binding.isPresent()) {
                ElderlyIdResolver.ElderlyBinding b = binding.get();
                
                ElderlyContext.ElderlyInfo info = new ElderlyContext.ElderlyInfo(
                    userId,
                    b.getElderlyId(),
                    b.getUserType(),
                    b.getRelativeId()
                );
                ElderlyContext.set(info);
            }
            // 若无法解析身份，继续（可能是管理端用户）
            
            return true;
        } catch (Exception e) {
            // 日志记录异常，但不中断请求
            System.err.println("ElderlyContextInterceptor error: " + e.getMessage());
            return true;
        }
    }
    
    /**
     * 完成处理：清理上下文
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                                Object handler, Exception ex) throws Exception {
        ElderlyContext.clear();
    }
    
    /**
     * 从 Authorization header 提取 token
     * 格式：Bearer <token>
     */
    private String extractTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
    
    /**
     * 从 token 解出 user_id
     * 
     * 注意：实际需要根据项目的 JWT 或 Token 实现具体解析
     * 这里提供示例框架，需要与项目的认证方案对接
     */
    private Long parseUserIdFromToken(String token) {
        // TODO: 集成项目实际的 JWT/Token 解析逻辑
        // 示例：
        // JwtTokenProvider jwtTokenProvider = ...
        // return jwtTokenProvider.getUserIdFromToken(token);
        
        // 临时返回 null，表示需要具体实现
        return null;
    }
}
