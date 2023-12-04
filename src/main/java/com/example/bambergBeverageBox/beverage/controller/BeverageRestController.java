package com.example.bambergBeverageBox.beverage.controller;

import com.example.bambergBeverageBox.beverage.model.BeverageAddRequest;
import com.example.bambergBeverageBox.beverage.model.BeverageSearchRequest;
import com.example.bambergBeverageBox.beverage.model.BeverageTotalRequest;
import com.example.bambergBeverageBox.beverage.service.BeverageModelService;
import com.example.bambergBeverageBox.rest.RestResponse;
import com.example.bambergBeverageBox.util.SearchPageRestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/beverage")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BeverageRestController {

    private final BeverageModelService beverageModelService;

    @PostMapping(value = "/save")
    public RestResponse saveBeverage(
            Model model,
            @Valid BeverageTotalRequest beverageTotalRequest) {
        try {

            List<BeverageAddRequest> beverageAddRequests = beverageTotalRequest.getBeverageAddRequestList();

            if (beverageAddRequests != null && !beverageAddRequests.isEmpty()) {
                beverageModelService.addBeveragePost(beverageAddRequests);
            }
        } catch (Exception e) {
            log.error("", e);
            return RestResponse.ofError("failure.common.action.data-save-failed");
        }
        return RestResponse.ofSuccess("Data saved success");
    }

    @GetMapping(value = "/list")
    public SearchPageRestResponse searchOfficeUnit(@RequestParam(required = false) Integer pageNo,
                                                   @RequestParam(required = false) Integer pageSize,
                                                   @RequestParam(required = false) Long beverageId,
                                                   @RequestParam(required = false) String beverageNameEn,
                                                   @RequestParam(required = false) String beverageNameDe

    ) {
        BeverageSearchRequest request = BeverageSearchRequest.builder()
                .beverageId(beverageId)
                .beverageNameEn(beverageNameEn)
                .beverageNameDe(beverageNameDe)
                .pageNo(pageNo == null ? 1 : pageNo)
                .pageSize(pageSize == null ? 10 : pageSize)
                .build();
        return beverageModelService.searchBeverage(request);
    }

}
