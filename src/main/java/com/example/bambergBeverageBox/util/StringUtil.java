package com.example.bambergBeverageBox.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class StringUtil {


    public static final String SESSION_ATTRIBUTE_NAME_CART = "SESSION_CART";
    private static final Gson gson = new GsonBuilder().serializeNulls().create();
    public static <T> String toJsonString(T object) {
        return gson.toJson(object);
    }

}
