package com.example.bambergBeverageBox.security.controller;

import com.example.bambergBeverageBox.rest.RestResponse;
import com.example.bambergBeverageBox.user.model.UserCreationResponse;
import com.example.bambergBeverageBox.user.model.UserSignUpAddRequest;
import com.example.bambergBeverageBox.user.service.UserModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LoginRestController {

    private final UserModelService userModelService;
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "/registerNewUser")
    public RestResponse<UserSignUpAddRequest> registerNewUser(
            @Valid UserSignUpAddRequest userSignUpAddRequest
    ) {
        try {
            UserCreationResponse userCreationResponse = userModelService.saveNewUser(userSignUpAddRequest);
            if (userCreationResponse.isUserCreated()) return RestResponse.ofSuccess(userCreationResponse.getMsg());
            else return RestResponse.ofError(userCreationResponse.getMsg());

        } catch (Exception e) {
            log.error("", e);
            return RestResponse.ofError("Can not create new user!");
        }
    }

    /*@GetMapping("/signIn")
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
                //return RestResponse.ofSuccess(userCreationResponse.getMsg());
            } //else return RestResponse.ofError(userCreationResponse.getMsg());

        } catch (Exception e) {
            log.error("", e);
            //return RestResponse.ofError("User sign in failed!");
        }
    }*/
}
