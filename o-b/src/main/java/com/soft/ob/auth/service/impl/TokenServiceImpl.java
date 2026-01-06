package com.soft.ob.auth.service.impl;

import com.soft.ob.entity.Token;
import com.soft.ob.repository.TokenRepository;
import com.soft.ob.auth.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
    
    @Autowired
    private TokenRepository tokenRepository;
    
    private static final int TOKEN_EXPIRY_HOURS = 24; // 令牌有效期24小时
    
    @Override
    public Token createToken(String userId, String userType) {
        // 生成随机令牌
        String tokenValue = UUID.randomUUID().toString();
        
        // 创建令牌对象
        Token token = new Token();
        token.setToken(tokenValue);
        token.setUserId(userId);
        token.setUserType(userType);
        token.setCreateTime(LocalDateTime.now());
        token.setExpireTime(LocalDateTime.now().plusHours(TOKEN_EXPIRY_HOURS));
        token.setIsActive(true);
        token.setLastAccessTime(LocalDateTime.now());
        
        // 保存到数据库
        return tokenRepository.save(token);
    }
    
    @Override
    public Optional<Token> getTokenByToken(String token) {
        return tokenRepository.findByToken(token);
    }
    
    @Override
    public Optional<Token> getTokenByUserId(String userId, String userType) {
        return tokenRepository.findByUserIdAndUserType(userId, userType);
    }
    
    @Override
    public boolean validateToken(String token) {
        Optional<Token> tokenOptional = tokenRepository.findByToken(token);
        if (tokenOptional.isEmpty()) {
            return false;
        }
        
        Token tokenEntity = tokenOptional.get();
        
        // 检查令牌是否激活
        if (!tokenEntity.getIsActive()) {
            return false;
        }
        
        // 检查令牌是否过期
        if (tokenEntity.getExpireTime().isBefore(LocalDateTime.now())) {
            // 过期令牌标记为不激活
            tokenEntity.setIsActive(false);
            tokenRepository.save(tokenEntity);
            return false;
        }
        
        // 更新最后访问时间
        tokenEntity.setLastAccessTime(LocalDateTime.now());
        tokenRepository.save(tokenEntity);
        
        return true;
    }
    
    @Override
    public void invalidateToken(String token) {
        Optional<Token> tokenOptional = tokenRepository.findByToken(token);
        if (tokenOptional.isPresent()) {
            Token tokenEntity = tokenOptional.get();
            tokenEntity.setIsActive(false);
            tokenRepository.save(tokenEntity);
        }
    }
    
    @Override
    public void invalidateUserTokens(String userId) {
        tokenRepository.deleteByUserId(userId);
    }
    
    @Override
    public Token refreshToken(String token) {
        Optional<Token> tokenOptional = tokenRepository.findByToken(token);
        if (tokenOptional.isEmpty()) {
            throw new RuntimeException("Token not found");
        }
        
        Token oldToken = tokenOptional.get();
        
        // 生成新令牌
        String newTokenValue = UUID.randomUUID().toString();
        
        // 更新令牌信息
        oldToken.setToken(newTokenValue);
        oldToken.setCreateTime(LocalDateTime.now());
        oldToken.setExpireTime(LocalDateTime.now().plusHours(TOKEN_EXPIRY_HOURS));
        oldToken.setLastAccessTime(LocalDateTime.now());
        
        return tokenRepository.save(oldToken);
    }
}