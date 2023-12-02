package com.example.bambergBeverageBox.beverage.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BeverageAddRequest {

    private Long id;

    @NotNull(message = "Beverage Name English Required")
    private String beverageNameEn;

    @NotNull(message = "Beverage Name Deutsch Required")
    private String beverageNameDe;
}
