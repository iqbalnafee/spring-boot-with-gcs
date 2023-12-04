package com.example.bambergBeverageBox.bottle.model;

import lombok.Data;

@Data
public class BottleAddRequest {

    private Long id;
    private Long beverageId;
    private String bottleNameEn;
    private String bottleNameDe;
    private String bottlePic;
    private Double volume;
    private boolean isAlcoholic = false;
    private Double volumePercent;
    private Double price;
    private String supplier;
    private Integer inStock;
}
