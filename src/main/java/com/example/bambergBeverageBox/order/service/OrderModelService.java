package com.example.bambergBeverageBox.order.service;


import com.example.bambergBeverageBox.cart.model.UserOrderPaymentResponse;
import com.example.bambergBeverageBox.order.model.OrderCreationResponse;
import com.example.bambergBeverageBox.order.model.OrderViewResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderModelService {

    private final OrderService orderService;

    public OrderCreationResponse placeOrder(UserOrderPaymentResponse userOrderPaymentResponse,
                                            Authentication authentication,
                                            final HttpSession session) {
        return orderService.placeOrder(userOrderPaymentResponse, authentication, session);
    }

    public void getPreviousOrders(Model model, Authentication authentication) {
        OrderViewResponse orderViewResponse = orderService.getPreviousOrders(authentication);
        model.addAttribute("orderViewResponseList", orderViewResponse);
    }
}
