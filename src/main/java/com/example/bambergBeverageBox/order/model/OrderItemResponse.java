package com.example.bambergBeverageBox.order.model;

import lombok.Data;

@Data
public class OrderItemResponse {

    private Long orderItemId;
    private Long orderId;
    private Long addedItemId;
    private String addedItemName;
    private String addedItemPic;
    private Double addedItemPrice;
    private Integer addedItemQuantity;
    private Double totalPricePerAddedItem;
}
