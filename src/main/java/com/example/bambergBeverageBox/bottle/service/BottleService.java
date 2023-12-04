package com.example.bambergBeverageBox.bottle.service;

import com.example.bambergBeverageBox.bottle.model.Bottle;
import com.example.bambergBeverageBox.bottle.model.BottleSearchRequest;
import com.example.bambergBeverageBox.util.SearchPageRestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BottleService {

    private final BottleRepository bottleRepository;

    public Page<Bottle> getBottlesByNameEnAndNameDe(String nameEn, String nameDe,
                                                    Integer pageNo, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(List.of(
                Sort.Order.desc("id")
        )));

        return bottleRepository.findAll(pageable);


    }

    public SearchPageRestResponse searchBottle(BottleSearchRequest request) {
        Page<Bottle> bottles = getBottlesByNameEnAndNameDe(request.getBottleNameEn(),
                request.getBottleNameDe(),
                request.getPageNo(),
                request.getPageSize());

        return SearchPageRestResponse.builder()
                .pageNo(request.getPageNo())
                .pageSize(request.getPageSize())
                .totalCount(bottles.getTotalElements())
                .success(true)
                .list(bottles.getContent())
                .build();
    }
}
