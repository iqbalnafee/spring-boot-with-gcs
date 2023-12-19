package com.example.bambergBeverageBox.jwt.service;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class JwtTokenBlacklist {

    private final Set<String> invalidatedTokens;

    public JwtTokenBlacklist() {
        this.invalidatedTokens = new HashSet<>();
    }

    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }

    public boolean isTokenInvalid(String token) {
        return invalidatedTokens.contains(token);
    }
}
