package com.example.bambergBeverageBox.security.controller;

import com.example.bambergBeverageBox.jwt.service.JwtService;
import com.example.bambergBeverageBox.jwt.service.JwtTokenHolder;
import com.example.bambergBeverageBox.user.model.UserCreationResponse;
import com.example.bambergBeverageBox.user.model.UserSignUpAddRequest;
import com.example.bambergBeverageBox.user.service.UserModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor( onConstructor_ = { @Autowired} )
public class LoginController {
    private final UserModelService userModelService;
    private final AuthenticationManager authenticationManager;
    @GetMapping( "/login" )
    public String login(
            Model model
    ) {
        //jwtService.invalidateToken(JwtTokenHolder.getToken());
        return "security/login";
    }

    /*@PostMapping("/signIn")
    public void signIn(
            @Valid UserSignUpAddRequest userSignUpAddRequest
    ) {


        try {
            UserCreationResponse userCreationResponse = userModelService.signIn(userSignUpAddRequest);
            if (userCreationResponse.isUserCreated()) {
                Authentication authentication =
                        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userSignUpAddRequest.getUserName(),
                                userSignUpAddRequest.getSignUpPassword()));
                //SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println(authentication.toString());
            }

        } catch (Exception e) {
        }
    }*/
}
