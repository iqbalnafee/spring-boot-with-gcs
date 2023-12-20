package com.example.bambergBeverageBox.beverage.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class BeverageSessionResponse implements Serializable {

    private Long beverageId;
    private Integer productType;
    private Long addedItemId;
    private String addedItemName;
    private String addedItemPic;
    private Double addedItemPrice = 0.0;
    private Integer addedItemQuantity;
    private Double totalPricePerAddedItem = 0.0;

}
