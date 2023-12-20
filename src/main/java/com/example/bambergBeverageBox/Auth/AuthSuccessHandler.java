package com.example.bambergBeverageBox.Auth;

import com.example.bambergBeverageBox.jwt.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        log.info("onAuthenticationSuccess is called");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();


        jwtService.createTokenAndSetInCookie(request, response, userDetails);
        super.onAuthenticationSuccess(request, response, authentication);
        response.sendRedirect("/");
    }


}
