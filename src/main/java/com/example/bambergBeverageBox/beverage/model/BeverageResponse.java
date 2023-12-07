package com.example.bambergBeverageBox.beverage.model;
import com.example.bambergBeverageBox.bottle.model.BottleResponse;
import lombok.Data;


@Data
public class BeverageResponse {

    Long beverageId;
    String beverageNameEn;
    String beverageNameDe;
    Integer productType;

    /*for bottles start*/

    private BottleResponse bottleResponse;

    /*for bottles end*/

    /*for crate start*/

    /*for crate end*/

}
