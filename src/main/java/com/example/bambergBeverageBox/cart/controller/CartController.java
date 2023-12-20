package com.example.bambergBeverageBox.cart.controller;

import com.example.bambergBeverageBox.annotations.TitleAndContent;
import com.example.bambergBeverageBox.base.MVCController;
import com.example.bambergBeverageBox.enums.Menu;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/cart")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CartController extends MVCController {


    @GetMapping(value = "/view")
    @TitleAndContent(title = "View Cart", content = "cart/view", activeMenu = Menu.CART)
    public String add(
            Model model, final HttpSession session, Authentication authentication
    ) {
        setTotalItemCountToCartFromSession(model,session,authentication);
        getItemFromSessionCart(model,session);
        return viewRoot;
    }

}
