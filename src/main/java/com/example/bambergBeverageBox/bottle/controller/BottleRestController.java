package com.example.bambergBeverageBox.bottle.controller;

import com.example.bambergBeverageBox.bottle.model.BottleSearchRequest;
import com.example.bambergBeverageBox.bottle.service.BottleModelService;
import com.example.bambergBeverageBox.util.SearchPageRestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/bottle")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BottleRestController {


    private final BottleModelService bottleModelService;

    @GetMapping(value = "/list")
    public SearchPageRestResponse searchOfficeUnit(@RequestParam(required = false) Integer pageNo,
                                                   @RequestParam(required = false) Integer pageSize,
                                                   @RequestParam(required = false) Long bottleId,
                                                   @RequestParam(required = false) String bottleNameEn,
                                                   @RequestParam(required = false) String bottleNameDe

    ) {
        BottleSearchRequest request = BottleSearchRequest.builder()
                .bottleId(bottleId)
                .bottleNameEn(bottleNameEn)
                .bottleNameDe(bottleNameDe)
                .pageNo(pageNo == null ? 1 : pageNo)
                .pageSize(pageSize == null ? 10 : pageSize)
                .build();
        return bottleModelService.searchBottle(request);
    }

}
