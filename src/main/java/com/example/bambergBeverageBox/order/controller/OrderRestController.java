package com.example.bambergBeverageBox.order.controller;


import com.example.bambergBeverageBox.cart.model.UserOrderPaymentResponse;
import com.example.bambergBeverageBox.order.model.OrderCreationResponse;
import com.example.bambergBeverageBox.order.service.OrderModelService;
import com.example.bambergBeverageBox.rest.RestResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderRestController {

    private final OrderModelService orderModelService;
    @PostMapping(value = "/placeOrder")
    public RestResponse<?> placeOrder(
            @Valid UserOrderPaymentResponse userOrderPaymentResponse,
            Authentication authentication,
            final HttpSession session
            ) {
        try {
            OrderCreationResponse orderCreationResponse = orderModelService.placeOrder(userOrderPaymentResponse,
                    authentication, session);
            if(orderCreationResponse.isOrderPlaced()){
                return RestResponse.ofSuccess(orderCreationResponse.getMsg());
            }
            else return RestResponse.ofError(orderCreationResponse.getMsg());

        } catch (Exception e) {
            log.error("", e);
            return RestResponse.ofError("Can not place order!");
        }
    }
}
