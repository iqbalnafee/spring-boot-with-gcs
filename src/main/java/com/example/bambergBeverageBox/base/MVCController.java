package com.example.bambergBeverageBox.base;

import com.example.bambergBeverageBox.beverage.model.BeverageSessionResponse;
import com.example.bambergBeverageBox.util.StringUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MVCController {
    protected String viewRoot = "layout";

    public void setTotalItemCountToCartFromSession(Model model, final HttpSession session, Authentication authentication) {
        List<BeverageSessionResponse> cartItems = (List<BeverageSessionResponse>) session.getAttribute(
                StringUtil.SESSION_ATTRIBUTE_NAME_CART);

        if (cartItems != null && !cartItems.isEmpty())
            model.addAttribute("totalItemAddedToCart", cartItems.size());
        else model.addAttribute("totalItemAddedToCart", 0);

        model.addAttribute("isAuthenticated", false);
        if (authentication != null) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            model.addAttribute("isAuthenticated", true);
        }


    }

    public void getItemFromSessionCart(Model model, final HttpSession session) {

        List<BeverageSessionResponse> cartItems = (List<BeverageSessionResponse>) session.getAttribute(
                StringUtil.SESSION_ATTRIBUTE_NAME_CART);
        Map<Long, BeverageSessionResponse> map = new HashMap<>();

        Double totalPriceOfAddedItems = 0.0;

        if (cartItems != null && !cartItems.isEmpty()) {

            for (BeverageSessionResponse item : cartItems) {
                Double price = item.getAddedItemPrice();
                totalPriceOfAddedItems += price;
            }
            map = StringUtil.getUniqueItemFromSessionCart(cartItems);
        }
        model.addAttribute("itemAddedToCart", map);
        model.addAttribute("totalPriceOfAddedItems", StringUtil.df.format((totalPriceOfAddedItems)));
    }
}
