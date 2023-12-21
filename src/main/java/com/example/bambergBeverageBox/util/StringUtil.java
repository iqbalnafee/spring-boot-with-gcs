package com.example.bambergBeverageBox.util;


import com.example.bambergBeverageBox.beverage.model.BeverageSessionResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StringUtil {


    public static final String SESSION_ATTRIBUTE_NAME_CART = "SESSION_CART";

    public static final DecimalFormat df = new DecimalFormat("0.00");
    private static final Gson gson = new GsonBuilder().serializeNulls().create();
    public static <T> String toJsonString(T object) {
        return gson.toJson(object);
    }

    public static Map<Long, BeverageSessionResponse> getUniqueItemFromSessionCart(List<BeverageSessionResponse> cartItems){
        Map<Long, BeverageSessionResponse> map = new HashMap<>();
        for (BeverageSessionResponse item : cartItems) {
            Double price = item.getAddedItemPrice();
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
        return map;
    }

}
