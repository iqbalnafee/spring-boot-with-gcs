package com.example.bambergBeverageBox.cart.service;

import com.example.bambergBeverageBox.cart.model.UserOrderPaymentResponse;
import com.example.bambergBeverageBox.order.model.OrderCreationResponse;
import com.example.bambergBeverageBox.user.model.User;
import com.example.bambergBeverageBox.user.service.UserModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CartModelService {
    private final UserModelService userModelService;
    public void getUserCartPaymentModel(Model model) {
        UserOrderPaymentResponse userOrderPaymentResponse = new UserOrderPaymentResponse();

        if(model.getAttribute("username") != null){
            String userName = (String) model.getAttribute("username");
            User user = userModelService.findUserByUserName(userName);
            if(user != null){
                userOrderPaymentResponse.setFullName(user.getFirstName()+" "+user.getLastName());
                userOrderPaymentResponse.setEmail(user.getEmail());
            }
        }
        model.addAttribute("userOrderPaymentResponse", userOrderPaymentResponse);
    }


}
