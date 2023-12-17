package com.example.bambergBeverageBox.user.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserCreationResponse {

    private String msg;
    private boolean userCreated = false;
}
