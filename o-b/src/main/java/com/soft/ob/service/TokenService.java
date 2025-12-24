package com.soft.ob.service;

import com.soft.ob.entity.Token;
import java.util.Optional;

public interface TokenService {
    Token createToken(String userId, String userType);
    Optional<Token> getTokenByToken(String token);
    Optional<Token> getTokenByUserId(String userId, String userType);
    boolean validateToken(String token);
    void invalidateToken(String token);
    void invalidateUserTokens(String userId);
    Token refreshToken(String token);
}