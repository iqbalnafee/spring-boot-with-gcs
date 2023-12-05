package com.example.bambergBeverageBox.products.service;

import com.example.bambergBeverageBox.beverage.model.BeverageResponse;
import com.example.bambergBeverageBox.beverage.service.BeverageModelService;
import com.example.bambergBeverageBox.products.model.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductsModelService {

    private final BeverageModelService beverageModelService;

    public void getAllProducts(Model model) {
        List<BeverageResponse> beverageResponseList =
                beverageModelService.getAllBeverageResponseList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setBeverageResponseList(beverageResponseList);
        model.addAttribute("productResponse",productResponse);
    }
}
