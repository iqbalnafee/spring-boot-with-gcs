package com.example.bambergBeverageBox.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    BAD_REQUEST(400),
    WRONG_CREDENTIAL(401),
    NOT_FOUND(404),
    OK(200);

    private final Integer errorCode;

}
