package com.example.bambergBeverageBox.order.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCreationResponse {

    private String msg;
    private boolean orderPlaced = false;
}
