package com.example.bambergBeverageBox.cart.controller;

import com.example.bambergBeverageBox.annotations.TitleAndContent;
import com.example.bambergBeverageBox.base.MVCController;
import com.example.bambergBeverageBox.enums.Menu;
import com.example.bambergBeverageBox.util.StringUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/cart")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CartController extends MVCController {

    @GetMapping(value = "/view")
    @TitleAndContent(title = "View Cart", content = "cart/view", activeMenu = Menu.CART)
    public String add(
            Model model, final HttpSession session
    ) {

        List<String> bottleResponses = (List<String>) session.getAttribute(StringUtil.SESSION_ATTRIBUTE_NAME_CART);
        if (bottleResponses != null && bottleResponses.size() > 0)
            model.addAttribute("itemAddedToCart", bottleResponses.size());
        else model.addAttribute("itemAddedToCart", 0);

        return viewRoot;
    }

}
