package com.example.bambergBeverageBox.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductType {
    BOTTLE(1),
    CRATE(2);

    private int productType;
}
