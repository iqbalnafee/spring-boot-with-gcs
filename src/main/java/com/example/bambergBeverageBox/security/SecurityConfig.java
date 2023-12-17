package com.example.bambergBeverageBox.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/login").permitAll()
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/beverage/**").hasRole("ADMIN")
                                .requestMatchers("/bottle/**").hasRole("ADMIN")
                                .requestMatchers("/crate/**").hasRole("ADMIN")
                )
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .headers()
                .xssProtection(); // Set custom entry point

        return http.build();
    }
}
