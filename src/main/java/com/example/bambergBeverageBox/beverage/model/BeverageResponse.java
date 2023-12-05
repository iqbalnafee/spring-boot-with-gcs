package com.example.bambergBeverageBox.beverage.model;
import lombok.Data;


@Data
public class BeverageResponse {

    Long beverageId;
    String beverageNameEn;
    String beverageNameDe;
    Integer productType;

    /*for bottles start*/

    private String bottleNameEn;
    private String bottleNameDe;
    private String bottlePic;
    private Double volume;
    private boolean isAlcoholic;
    private Double volumePercent;
    private String price;
    private String supplier;
    private String inStock;

    /*for bottles end*/

    /*for crate start*/

    /*for crate end*/

}
