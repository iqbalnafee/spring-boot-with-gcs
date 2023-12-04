package com.example.bambergBeverageBox.util;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@Data
@Builder
@ToString
public class DropdownOptionResponse {
    private Long id;
    private String nameEn;
    private String nameDe;
}
