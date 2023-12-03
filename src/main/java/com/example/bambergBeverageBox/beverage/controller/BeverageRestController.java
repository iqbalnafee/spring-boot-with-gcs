package com.example.bambergBeverageBox.beverage.controller;

import com.example.bambergBeverageBox.beverage.model.BeverageAddRequest;
import com.example.bambergBeverageBox.beverage.model.BeverageTotalRequest;
import com.example.bambergBeverageBox.beverage.service.BeverageModelService;
import com.example.bambergBeverageBox.rest.RestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
