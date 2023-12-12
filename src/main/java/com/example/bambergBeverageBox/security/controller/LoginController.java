package com.example.bambergBeverageBox.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor( onConstructor_ = { @Autowired} )
public class LoginController {
    @GetMapping( "/login" )
    public String login(
            Model model
    ) {
        return "security/login";
    }
}
