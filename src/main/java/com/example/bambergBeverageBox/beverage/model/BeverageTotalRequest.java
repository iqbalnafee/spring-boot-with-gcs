package com.example.bambergBeverageBox.beverage.model;

import lombok.Data;

import java.util.List;

@Data
public class BeverageTotalRequest {

    List<BeverageAddRequest> beverageAddRequestList;
    Boolean editable = false;
}
