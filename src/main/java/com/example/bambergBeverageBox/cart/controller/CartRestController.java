package com.example.bambergBeverageBox.cart.controller;

import com.example.bambergBeverageBox.beverage.model.BeverageSessionResponse;
import com.example.bambergBeverageBox.bottle.model.BottleResponse;
import com.example.bambergBeverageBox.rest.RestResponse;
import com.example.bambergBeverageBox.util.StringUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CartRestController {


    @GetMapping(value = "/setCartSessionData")
    public RestResponse<?> setCartSessionData(
            @Valid BeverageSessionResponse beverageSessionResponse, final HttpSession session
    ) {
        try {
            List<BeverageSessionResponse> cartItems = (List<BeverageSessionResponse>) session.getAttribute(StringUtil.SESSION_ATTRIBUTE_NAME_CART);
            if (cartItems == null) cartItems = new ArrayList<>();
            cartItems.add(beverageSessionResponse);
            session.setAttribute(StringUtil.SESSION_ATTRIBUTE_NAME_CART, cartItems);

            return RestResponse.ofSuccess("set done session data!");

        } catch (Exception e) {
            return RestResponse.ofError("Can not set cart session data!");
        }
    }

}
