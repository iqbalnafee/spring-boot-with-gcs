package com.example.bambergBeverageBox.order.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private String address;
    private LocalDateTime orderDate;
    private Double totalPrice;
    private List<OrderItemResponse> orderItemResponseList;

}
