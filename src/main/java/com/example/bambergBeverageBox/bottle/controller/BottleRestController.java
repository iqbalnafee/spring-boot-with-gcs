package com.example.bambergBeverageBox.bottle.controller;

import com.example.bambergBeverageBox.bottle.model.BottleAddRequest;
import com.example.bambergBeverageBox.bottle.model.BottleSearchRequest;
import com.example.bambergBeverageBox.bottle.model.BottleTotalRequest;
import com.example.bambergBeverageBox.bottle.service.BottleModelService;
import com.example.bambergBeverageBox.rest.RestResponse;
import com.example.bambergBeverageBox.util.SearchPageRestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping(value = "/save")
    public RestResponse saveBottle(
            @Valid BottleTotalRequest bottleTotalRequest) {
        try {

            List<BottleAddRequest> bottleAddRequestList = bottleTotalRequest.getBottleAddRequestList();

            if (bottleAddRequestList != null && !bottleAddRequestList.isEmpty()) {
                bottleModelService.addBottlePost(bottleAddRequestList);
            }
        } catch (Exception e) {
            log.error("", e);
            return RestResponse.ofError("Data saved failure");
        }
        return RestResponse.ofSuccess("Data saved success");
    }

}
