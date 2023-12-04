package com.example.bambergBeverageBox.bottle.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BottleSearchRequest {

    private String bottleNameEn;
    private String bottleNameDe;
    private Long bottleId;
    private Integer pageNo;
    private Integer pageSize;
}
