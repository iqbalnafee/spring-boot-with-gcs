package com.example.bambergBeverageBox.jwt.service;

public class JwtTokenHolder {

    private static final ThreadLocal<String> JWT_TOKEN_HOLDER_THREAD = new ThreadLocal<>();

    public static String getToken() {
        return JWT_TOKEN_HOLDER_THREAD.get();
    }

    public static void setToken(String token) {
        JWT_TOKEN_HOLDER_THREAD.set(token);
    }

    public static void removeToken() {
        JWT_TOKEN_HOLDER_THREAD.remove();
    }

}
