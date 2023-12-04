package com.example.bambergBeverageBox.util;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchPageRestResponse {
    private Integer pageNo;
    private Integer pageSize;
    private long totalCount;
    private List<?> list;
    private boolean success;
    private String message;
}
