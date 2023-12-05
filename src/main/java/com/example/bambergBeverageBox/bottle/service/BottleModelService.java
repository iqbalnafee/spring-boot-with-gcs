package com.example.bambergBeverageBox.bottle.service;

import com.example.bambergBeverageBox.bottle.model.Bottle;
import com.example.bambergBeverageBox.bottle.model.BottleAddRequest;
import com.example.bambergBeverageBox.bottle.model.BottleSearchRequest;
import com.example.bambergBeverageBox.bottle.model.BottleTotalRequest;
import com.example.bambergBeverageBox.util.SearchPageRestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BottleModelService {

    private final BottleService bottleService;

    public void addBottleModel(Model model) {
        BottleTotalRequest bottleTotalRequest = new BottleTotalRequest();
        bottleTotalRequest.setBottleAddRequestList(Collections.singletonList(new BottleAddRequest()));
        model.addAttribute("bottleTotalRequest", bottleTotalRequest);
    }

    public void editBottleModel(Model model, Long id) {
        BottleTotalRequest bottleTotalRequest = new BottleTotalRequest();
        bottleTotalRequest.setBottleAddRequestList(Collections.singletonList(getAddRequestFromEntity(id)));
        bottleTotalRequest.setEditable(true);
        model.addAttribute("bottleTotalRequest", bottleTotalRequest);
    }

    public BottleAddRequest getAddRequestFromEntity(Long id) {
        BottleAddRequest bottleAddRequest = new BottleAddRequest();
        Optional<Bottle> optionalBottle = bottleService.findById(id);
        if (optionalBottle.isPresent()) {
            Bottle bottle = optionalBottle.get();
            bottleAddRequest.setId(bottle.getId());
            bottleAddRequest.setBeverageId(bottle.getBeverageId());
            bottleAddRequest.setBottleNameEn(bottle.getBottleNameEn());
            bottleAddRequest.setBottleNameDe(bottle.getBottleNameDe());
            bottleAddRequest.setBottlePic(bottle.getBottlePic());
            bottleAddRequest.setVolume(bottle.getVolume());
            bottleAddRequest.setAlcoholic(bottle.getVolumePercent() > 10);
            bottleAddRequest.setVolumePercent(bottle.getVolumePercent());
            bottleAddRequest.setPrice(bottle.getPrice());
            bottleAddRequest.setSupplier(bottle.getSupplier());
            bottleAddRequest.setInStock(bottle.getInStock());

        }
        return bottleAddRequest;
    }

    public SearchPageRestResponse searchBottle(BottleSearchRequest request) {
        return bottleService.searchBottle(request);
    }

    public void addBottlePost(List<BottleAddRequest> bottleAddRequestList) {
        bottleAddRequestList.forEach(bottleService::save);
    }
}
