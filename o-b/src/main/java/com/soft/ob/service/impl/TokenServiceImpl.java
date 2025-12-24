package com.soft.ob.service.impl;

import com.soft.ob.entity.Token;
import com.soft.ob.repository.TokenRepository;
import com.soft.ob.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
    
    @Autowired
    private TokenRepository tokenRepository;
    
    // Token validity period in minutes (24 hours = 1440 minutes)
    private static final int TOKEN_VALIDITY_MINUTES = 1440;
    
    @Override
    public Token createToken(String userId, String userType) {
        // Invalidate any existing tokens for this user
        invalidateUserTokens(userId);
        
        // Create new token
        Token token = new Token();
        token.setToken(generateToken());
        token.setUserId(userId);
        token.setUserType(userType);
        token.setCreateTime(LocalDateTime.now());
        token.setExpireTime(LocalDateTime.now().plusMinutes(TOKEN_VALIDITY_MINUTES));
        token.setIsActive(true);
        token.setLastAccessTime(LocalDateTime.now());
        
        return tokenRepository.save(token);
    }
    
    @Override
    public Optional<Token> getTokenByToken(String tokenValue) {
        Optional<Token> tokenOpt = tokenRepository.findByToken(tokenValue);
        if (tokenOpt.isPresent()) {
            Token token = tokenOpt.get();
            // Update last access time
            token.setLastAccessTime(LocalDateTime.now());
            tokenRepository.save(token);
        }
        return tokenOpt;
    }
    
    @Override
    public Optional<Token> getTokenByUserId(String userId, String userType) {
        return tokenRepository.findByUserIdAndUserType(userId, userType);
    }
    
    @Override
    public boolean validateToken(String tokenValue) {
        Optional<Token> tokenOpt = tokenRepository.findByToken(tokenValue);
        if (tokenOpt.isPresent()) {
            Token token = tokenOpt.get();
            if (token.getIsActive() && !isTokenExpired(token)) {
                // Update last access time
                token.setLastAccessTime(LocalDateTime.now());
                tokenRepository.save(token);
                return true;
            } else {
                // Token is expired, invalidate it
                token.setIsActive(false);
                tokenRepository.save(token);
            }
        }
        return false;
    }
    
    @Override
    public void invalidateToken(String tokenValue) {
        Optional<Token> tokenOpt = tokenRepository.findByToken(tokenValue);
        if (tokenOpt.isPresent()) {
            Token token = tokenOpt.get();
            token.setIsActive(false);
            tokenRepository.save(token);
        }
    }
    
    @Override
    public void invalidateUserTokens(String userId) {
        tokenRepository.deleteByUserId(userId);
    }
    
    @Override
    public Token refreshToken(String tokenValue) {
        Optional<Token> tokenOpt = tokenRepository.findByToken(tokenValue);
        if (tokenOpt.isPresent()) {
            Token token = tokenOpt.get();
            if (token.getIsActive() && !isTokenExpired(token)) {
                // Update expiration time
                token.setExpireTime(LocalDateTime.now().plusMinutes(TOKEN_VALIDITY_MINUTES));
                token.setLastAccessTime(LocalDateTime.now());
                return tokenRepository.save(token);
            }
        }
        throw new RuntimeException("Invalid or expired token");
    }
    
    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "") + System.currentTimeMillis();
    }
    
    private boolean isTokenExpired(Token token) {
        return token.getExpireTime().isBefore(LocalDateTime.now());
    }
}