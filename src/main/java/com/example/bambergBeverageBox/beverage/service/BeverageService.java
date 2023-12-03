package com.example.bambergBeverageBox.beverage.service;

import com.example.bambergBeverageBox.beverage.model.Beverage;
import com.example.bambergBeverageBox.beverage.model.BeverageAddRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BeverageService {

    private final BeverageRepository beverageRepository;
    public Beverage save(BeverageAddRequest beverageAddRequest) {
        Beverage beverage = new Beverage();
        if(beverageAddRequest.getId() != null) {
            Optional<Beverage> optionalBeverage = beverageRepository.findById(beverageAddRequest.getId());
            if(optionalBeverage.isPresent()) beverage = optionalBeverage.get();
        }
        getBeverageFromAddRequest(beverage,beverageAddRequest);
        return beverageRepository.save(beverage);
    }

    public Beverage getBeverageFromAddRequest(Beverage beverage, BeverageAddRequest beverageAddRequest) {

        beverage.setBeverageNameEn(beverageAddRequest.getBeverageNameEn().trim());
        beverage.setBeverageNameDe(beverageAddRequest.getBeverageNameDe().trim());

        return beverage;
    }
}
