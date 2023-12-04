package com.example.bambergBeverageBox.beverage.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BeverageSearchRequest {

    private String beverageNameEn;
    private String beverageNameDe;
    private Long beverageId;
    private Integer pageNo;
    private Integer pageSize;
}
