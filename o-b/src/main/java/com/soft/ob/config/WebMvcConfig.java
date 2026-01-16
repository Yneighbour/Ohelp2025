package com.soft.ob.config;

import com.soft.ob.common.interceptor.ElderlyContextInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc 配置：注册拦截器、CORS、消息转换器等
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Autowired
    private ElderlyContextInterceptor elderlyContextInterceptor;
    
    /**
     * 注册拦截器
     * 
     * 覆盖范围：所有 /api/** 请求
     * 排除范围：登录、注册、token验证等认证接口
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(elderlyContextInterceptor)
            .addPathPatterns("/api/**")
            .excludePathPatterns(
                "/api/auth/login",
                "/api/auth/register",
                "/api/auth/validate/**"
            );
    }
}
