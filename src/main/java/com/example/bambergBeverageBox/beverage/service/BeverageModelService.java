package com.example.bambergBeverageBox.beverage.service;

import com.example.bambergBeverageBox.beverage.model.BeverageAddRequest;
import com.example.bambergBeverageBox.beverage.model.BeverageTotalRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BeverageModelService {

    private final BeverageService beverageService;

    public void addBeverageModel(Model model) {
        List<BeverageAddRequest> beverageAddRequestList = new ArrayList<>();
        beverageAddRequestList.add(new BeverageAddRequest());
        BeverageTotalRequest beverageTotalRequest = new BeverageTotalRequest();
        beverageTotalRequest.setBeverageAddRequestList(beverageAddRequestList);
        model.addAttribute("beverageTotalRequest", beverageTotalRequest);
    }


    @Transactional
    public void addBeveragePost(List<BeverageAddRequest> beverageAddRequests) {
        beverageAddRequests.forEach(beverageService::save);
    }


}
