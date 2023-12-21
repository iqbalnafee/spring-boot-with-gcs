package com.example.bambergBeverageBox.cart.model;

import lombok.Data;

@Data

public class UserOrderPaymentResponse {
    private String fullName = "Full Name";
    private String email = "your-email@example.com";
    private String street = "An Der Weberei";
    private String streetNumber = "5";
    private String city = "Bamberg";
    private String state = "Bavaria";
    private String postalCode = "96049";

}
