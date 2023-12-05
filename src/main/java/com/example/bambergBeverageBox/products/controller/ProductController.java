package com.example.bambergBeverageBox.products.controller;

import com.example.bambergBeverageBox.annotations.TitleAndContent;
import com.example.bambergBeverageBox.base.MVCController;
import com.example.bambergBeverageBox.enums.Menu;
import com.example.bambergBeverageBox.products.service.ProductsModelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductController extends MVCController {

    private final ProductsModelService productsModelService;
    @GetMapping
    @TitleAndContent(title = "Products", content = "products/view", activeMenu = Menu.PRODUCTS)
    public String view(
            Model model
    ) {

        log.debug("Rendering Product Page");
        productsModelService.getAllProducts(model);
        return viewRoot;
    }

}
