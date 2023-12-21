package com.example.bambergBeverageBox.order.controller;

import com.example.bambergBeverageBox.annotations.TitleAndContent;
import com.example.bambergBeverageBox.base.MVCController;
import com.example.bambergBeverageBox.enums.Menu;
import com.example.bambergBeverageBox.order.service.OrderModelService;
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
@RequestMapping(path = "/orders")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderController  extends MVCController {

    private final OrderModelService orderModelService;
    @GetMapping(value = "/view")
    @TitleAndContent(title = "View Orders", content = "order/view", activeMenu = Menu.ORDERS)
    public String add(
            Model model, final HttpSession session, Authentication authentication
    ) {
        setTotalItemCountToCartFromSession(model,session,authentication);
        getItemFromSessionCart(model,session);
        orderModelService.getPreviousOrders(model,authentication);
        return viewRoot;
    }

}
