package com.example.bambergBeverageBox.order.model;

import lombok.Data;

import java.util.List;

@Data
public class OrderViewResponse {
    private List<OrderResponse> orderResponseList;
}
