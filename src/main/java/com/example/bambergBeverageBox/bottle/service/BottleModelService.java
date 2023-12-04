package com.example.bambergBeverageBox.bottle.service;

import com.example.bambergBeverageBox.bottle.model.BottleAddRequest;
import com.example.bambergBeverageBox.bottle.model.BottleSearchRequest;
import com.example.bambergBeverageBox.bottle.model.BottleTotalRequest;
import com.example.bambergBeverageBox.util.SearchPageRestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Collections;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BottleModelService {

    private final BottleService bottleService;
    public void addBottleModel(Model model) {
        BottleTotalRequest bottleTotalRequest = new BottleTotalRequest();
        bottleTotalRequest.setBottleAddRequestList(Collections.singletonList(new BottleAddRequest()));
        model.addAttribute("bottleTotalRequest", bottleTotalRequest);
    }

    public void editBeverageModel(Model model, Long id) {
    }

    public SearchPageRestResponse searchBottle(BottleSearchRequest request) {
        return bottleService.searchBottle(request);
    }
}
