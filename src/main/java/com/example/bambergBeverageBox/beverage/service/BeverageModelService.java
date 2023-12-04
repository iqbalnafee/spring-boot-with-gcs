package com.example.bambergBeverageBox.beverage.service;

import com.example.bambergBeverageBox.beverage.model.Beverage;
import com.example.bambergBeverageBox.beverage.model.BeverageAddRequest;
import com.example.bambergBeverageBox.beverage.model.BeverageSearchRequest;
import com.example.bambergBeverageBox.beverage.model.BeverageTotalRequest;
import com.example.bambergBeverageBox.util.SearchPageRestResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    public SearchPageRestResponse searchBeverage(BeverageSearchRequest request) {
        return beverageService.searchBeverage(request);
    }

    public void editBeverageModel(Model model, Long id) {
        List<BeverageAddRequest> beverageAddRequestList = new ArrayList<>();
        beverageAddRequestList.add(getAddRequestFromEntity(id));
        BeverageTotalRequest beverageTotalRequest = new BeverageTotalRequest();
        beverageTotalRequest.setBeverageAddRequestList(beverageAddRequestList);
        beverageTotalRequest.setEditable(true);
        model.addAttribute("beverageTotalRequest", beverageTotalRequest);
    }

    public BeverageAddRequest getAddRequestFromEntity(Long id){
        BeverageAddRequest beverageAddRequest = new BeverageAddRequest();
        Optional<Beverage> optionalBeverage = beverageService.findById(id);
        if(optionalBeverage.isPresent()){
            Beverage beverage = optionalBeverage.get();
            beverageAddRequest.setId(beverage.getId());
            beverageAddRequest.setBeverageNameEn(beverage.getBeverageNameEn());
            beverageAddRequest.setBeverageNameDe(beverage.getBeverageNameDe());
        }
        return beverageAddRequest;
    }

    public Object getBeverageDropdownList() {
        return beverageService.getBeverageDropdownList();
    }
}
