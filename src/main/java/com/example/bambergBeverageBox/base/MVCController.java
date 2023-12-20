package com.example.bambergBeverageBox.base;

import com.example.bambergBeverageBox.beverage.model.BeverageSessionResponse;
import com.example.bambergBeverageBox.util.StringUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MVCController {
    protected String viewRoot = "layout";

    public void setTotalItemCountToCartFromSession(Model model, final HttpSession session) {
        List<BeverageSessionResponse> cartItems = (List<BeverageSessionResponse>) session.getAttribute(
                StringUtil.SESSION_ATTRIBUTE_NAME_CART);

        if (cartItems != null && !cartItems.isEmpty())
            model.addAttribute("totalItemAddedToCart", cartItems.size());
        else model.addAttribute("totalItemAddedToCart", 0);
    }

    public void getItemFromSessionCart(Model model, final HttpSession session) {
        List<BeverageSessionResponse> cartItems = (List<BeverageSessionResponse>) session.getAttribute(
                StringUtil.SESSION_ATTRIBUTE_NAME_CART);

        if (cartItems != null && !cartItems.isEmpty()) {
            Map<Long,List<BeverageSessionResponse>> map = new HashMap<>();
            cartItems.forEach((item) -> {
                map.computeIfAbsent(item.getAddedItemId(), k -> new ArrayList<>()).add(item);
            });
            model.addAttribute("itemAddedToCart", map);
        }

        else model.addAttribute("itemAddedToCart", null);
    }
}
