package com.example.bambergBeverageBox.security;

import com.example.bambergBeverageBox.Auth.AuthSuccessHandler;
import com.example.bambergBeverageBox.Auth.AuthenticationFailureHandler;
import com.example.bambergBeverageBox.jwt.service.JwtAuthFilter;
import com.example.bambergBeverageBox.user.service.UserRepository;
import com.example.bambergBeverageBox.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@SuppressWarnings("deprecation")
public class SecurityConfig {


    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final AuthSuccessHandler authSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final UserService userService;

    @Autowired
    JwtAuthFilter jwtAuthFilter;



    private final String[] permitAllURL = {
            "/",
            "/login",
            "/signIn",
            "/api/login/**",
            "/css/**",
            "/images/**",
            "/js/**",
            "/metronic/**"
    };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers(permitAllURL).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/signIn")
                .successHandler(authSuccessHandler)
                .defaultSuccessUrl("/", true)
                .permitAll()
                .and()
                // other configurations...
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);



        /*http
                .csrf()
                .disable()

                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers(permitAllURL).permitAll()
                                .requestMatchers("/beverage/**").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers("/bottle/**").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers("/crate/**").hasAnyAuthority("ROLE_ADMIN")
                                .anyRequest().authenticated()
                )

                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .headers()
                .xssProtection(); // Set custom entry point*/

        return http.build();
    }

    /*@Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }*/

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ROLE_ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }


}
