package com.example.bambergBeverageBox.beverage.service;

import com.example.bambergBeverageBox.beverage.model.Beverage;
import com.example.bambergBeverageBox.beverage.model.BeverageAddRequest;
import com.example.bambergBeverageBox.beverage.model.BeverageResponse;
import com.example.bambergBeverageBox.beverage.model.BeverageSearchRequest;
import com.example.bambergBeverageBox.bottle.model.Bottle;
import com.example.bambergBeverageBox.bottle.service.BottleService;
import com.example.bambergBeverageBox.enums.ProductType;
import com.example.bambergBeverageBox.util.DropdownOptionResponse;
import com.example.bambergBeverageBox.util.SearchPageRestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BeverageService {

    private final BeverageRepository beverageRepository;
    private final BottleService bottleService;

    public Beverage save(BeverageAddRequest beverageAddRequest) {
        Beverage beverage = new Beverage();
        if (beverageAddRequest.getId() != null) {
            Optional<Beverage> optionalBeverage = beverageRepository.findById(beverageAddRequest.getId());
            if (optionalBeverage.isPresent()) beverage = optionalBeverage.get();
        }
        getBeverageFromAddRequest(beverage, beverageAddRequest);
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

    public List<Beverage> getAllBeverages() {
        return beverageRepository.findAll();
    }

    public List<DropdownOptionResponse> getBeverageDropdownList() {
        return getAllBeverages().stream()
                .map(this::buildDropdownOption)
                .collect(Collectors.toList());
    }

    public DropdownOptionResponse buildDropdownOption(Beverage beverage) {
        return DropdownOptionResponse.builder()
                .id(beverage.getId())
                .nameEn(beverage.getBeverageNameEn())
                .nameDe(beverage.getBeverageNameDe())
                .build();
    }

    public List<BeverageResponse> getAllBeverageResponseList() {
        List<BeverageResponse> beverageResponseList = new ArrayList<>();
        List<Beverage> beverages = beverageRepository.findAll();
        for (Beverage beverage : beverages) {
            List<Bottle> bottles = bottleService.findBottleByBeverageId(beverage.getId());
            for(Bottle bottle : bottles){
                BeverageResponse beverageResponse = new BeverageResponse();
                getBeverageResponse(beverage,bottle,beverageResponse);
                beverageResponseList.add(beverageResponse);
            }

        }
        return beverageResponseList;
    }

    private <T> void getBeverageResponse(Beverage beverage, T product, BeverageResponse beverageResponse) {

        beverageResponse.setBeverageId(beverage.getId());
        beverageResponse.setBeverageNameEn(beverage.getBeverageNameEn());
        beverageResponse.setBeverageNameDe(beverage.getBeverageNameDe());

        if(product.getClass().equals(Bottle.class)){

            Bottle bottle = (Bottle) product;
            beverageResponse.setProductType(ProductType.BOTTLE.getProductType());

            beverageResponse.setBottleNameEn(bottle.getBottleNameEn());
            beverageResponse.setBottleNameDe(bottle.getBottleNameDe());
            beverageResponse.setBottlePic(bottle.getBottlePic());
            beverageResponse.setVolume(bottle.getVolume());
            beverageResponse.setVolumePercent(bottle.getVolumePercent());
            beverageResponse.setAlcoholic(bottle.getVolumePercent()>10);
            beverageResponse.setPrice(bottle.getPrice()+" €");
            beverageResponse.setSupplier(bottle.getSupplier());
            beverageResponse.setInStock(bottle.getInStock()+" pieces left");
        }
    }
}
