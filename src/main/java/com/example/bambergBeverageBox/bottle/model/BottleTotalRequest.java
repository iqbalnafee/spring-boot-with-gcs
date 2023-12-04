package com.example.bambergBeverageBox.bottle.model;

import lombok.Data;

import java.util.List;

@Data
public class BottleTotalRequest {
    List<BottleAddRequest> bottleAddRequestList;
    Boolean editable = false;
}
