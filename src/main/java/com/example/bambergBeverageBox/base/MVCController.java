package com.example.bambergBeverageBox.base;

import com.example.bambergBeverageBox.beverage.model.BeverageSessionResponse;
import com.example.bambergBeverageBox.util.StringUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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
        Map<Long, BeverageSessionResponse> map = new HashMap<>();

        Double totalPriceOfAddedItems = 0.0;

        if (cartItems != null && !cartItems.isEmpty()) {

            for (BeverageSessionResponse item : cartItems) {
                Double price = item.getAddedItemPrice();
                totalPriceOfAddedItems += price;
                if (map.containsKey(item.getAddedItemId())) {
                    BeverageSessionResponse beverageSessionResponse = map.get(item.getAddedItemId());
                    beverageSessionResponse.setAddedItemQuantity(beverageSessionResponse.getAddedItemQuantity() + 1);
                    beverageSessionResponse.setTotalPricePerAddedItem(
                            Double.valueOf(StringUtil.df.format(price * beverageSessionResponse.getAddedItemQuantity())));
                    map.put(item.getAddedItemId(), beverageSessionResponse);
                } else {
                    item.setAddedItemQuantity(1);
                    item.setTotalPricePerAddedItem(price);
                    map.put(item.getAddedItemId(), item);
                }
            }


        }
        model.addAttribute("itemAddedToCart", map);
        model.addAttribute("totalPriceOfAddedItems", StringUtil.df.format((totalPriceOfAddedItems)));
    }
}
