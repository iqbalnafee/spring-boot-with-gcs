package com.example.bambergBeverageBox.user.service;

import com.example.bambergBeverageBox.user.model.UserCreationResponse;
import com.example.bambergBeverageBox.user.model.UserSignUpAddRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserModelService {

    private final UserService userService;
    public UserCreationResponse saveNewUser(UserSignUpAddRequest userSignUpAddRequest) {
        return userService.saveNewUser(userSignUpAddRequest);
    }

    public UserCreationResponse signIn(UserSignUpAddRequest userSignUpAddRequest) {
        return userService.signIn(userSignUpAddRequest);
    }
}
