package com.example.bambergBeverageBox.security.controller;

import com.example.bambergBeverageBox.rest.RestResponse;
import com.example.bambergBeverageBox.user.model.UserSignUpAddRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LoginRestController {

    @PostMapping(value = "/registerNewUser")
    public RestResponse<UserSignUpAddRequest> registerNewUser(
            @Valid UserSignUpAddRequest userSignUpAddRequest
    ) {
        try {
            return RestResponse.ofSuccess("User created successfully");
        } catch (Exception e) {
            log.error("", e);
            return RestResponse.ofError("Can not create new user");
        }
    }
}
