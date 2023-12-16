package com.example.bambergBeverageBox.user.model;

import lombok.Data;

@Data
public class UserSignUpAddRequest {

    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String signUpPassword;
}
