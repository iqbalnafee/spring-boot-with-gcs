package com.example.bambergBeverageBox.jwt.service;

import com.example.bambergBeverageBox.jwt.model.JwtResponseDTO;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component

public class JwtService {

    public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

    private static final String LOGIN_TOKEN = "login_token";

    private long accessTokenValidity = 60 * 60 * 1000;

    private final JwtParser jwtParser;

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    public JwtService() {
        this.jwtParser = Jwts.parser().setSigningKey(SECRET);
    }

    public String createToken(UserDetails userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request) {

        /*String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;*/

        String loginToken = null;

        if (request.getCookies() != null && request.getCookies().length > 0) {
            Map<String, String> cookieMap = Stream.of(request.getCookies())
                    .collect(Collectors.toMap(Cookie::getName, Cookie::getValue));
            loginToken = cookieMap.get(LOGIN_TOKEN);

        }

        return loginToken;

    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }

    public String getUserName(Claims claims) {
        return claims.getSubject();
    }

    private List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }

    @Transactional
    public void createTokenAndSetInCookie(HttpServletRequest request, HttpServletResponse response, UserDetails userDetails) {
        setTokenIntoCookie(userDetails, request, response, System.currentTimeMillis());
    }

    @Transactional
    public void setTokenIntoCookie(UserDetails userDetails, HttpServletRequest request,
                                   HttpServletResponse response, long time) {

        JwtResponseDTO jwtResponseDTO = JwtResponseDTO.builder()
                .accessToken(createToken(userDetails)).build();

        String tokens = jwtResponseDTO.getAccessToken();
        setTokenIntoCookie(LOGIN_TOKEN, tokens, response);
    }

    private void setTokenIntoCookie(String cookieName, String token, HttpServletResponse response) {

        Cookie cookie = new Cookie(cookieName, token);
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
    }

    public void invalidateToken(HttpServletResponse response) {
        invalidateToken(LOGIN_TOKEN, response);
    }

    private void invalidateToken(String tokenName, HttpServletResponse response) {
        Cookie cookie = new Cookie(tokenName, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}