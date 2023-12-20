package com.example.bambergBeverageBox.beverage.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class BeverageSessionResponse  implements Serializable {

    private Long beverageId;
    private Integer productType;
    private Long addedItemId;
    private String addedItemName;
    private String addedItemPic;
    private Double addedItemPrice;
    private Integer addedItemQuantity;

}
