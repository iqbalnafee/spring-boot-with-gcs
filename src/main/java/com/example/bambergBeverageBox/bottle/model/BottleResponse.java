package com.example.bambergBeverageBox.bottle.model;

import com.example.bambergBeverageBox.enums.ProductType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BottleResponse {
    private Long id;
    private Long beverageId;
    private String bottleNameEn;
    private String bottleNameDe;
    private String bottlePic;
    private Double volume;
    private boolean isAlcoholic = false;
    private Double volumePercent;
    private String price;
    private String supplier;
    private String inStock;
}
