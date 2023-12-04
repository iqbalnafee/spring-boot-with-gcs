package com.example.bambergBeverageBox.beverage.service;

import com.example.bambergBeverageBox.beverage.model.Beverage;
import com.example.bambergBeverageBox.beverage.model.BeverageAddRequest;
import com.example.bambergBeverageBox.beverage.model.BeverageSearchRequest;
import com.example.bambergBeverageBox.util.SearchPageRestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;

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

    public Page<Beverage> getBeverageByNameEnAndNameDe(String nameEn, String nameDe,
                                                                               Integer pageNo, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(List.of(
                Sort.Order.desc("id")
        )));

        return beverageRepository.findAll(pageable);


    }

    public SearchPageRestResponse searchBeverage(BeverageSearchRequest request) {

        Page<Beverage> beverages = getBeverageByNameEnAndNameDe(request.getBeverageNameEn(),
                request.getBeverageNameDe(),
                request.getPageNo(),
                request.getPageSize());

        return SearchPageRestResponse.builder()
                .pageNo(request.getPageNo())
                .pageSize(request.getPageSize())
                .totalCount(beverages.getTotalElements())
                .success(true)
                .list(beverages.getContent())
                .build();
    }

    public Optional<Beverage> findById(Long id) {
        return beverageRepository.findById(id);
    }
}
