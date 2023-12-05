package com.example.bambergBeverageBox.products.model;

import com.example.bambergBeverageBox.beverage.model.BeverageResponse;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    List<BeverageResponse> beverageResponseList;
}
