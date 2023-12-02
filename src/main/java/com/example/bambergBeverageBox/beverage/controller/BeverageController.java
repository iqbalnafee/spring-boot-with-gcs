package com.example.bambergBeverageBox.beverage.controller;

import com.example.bambergBeverageBox.annotations.TitleAndContent;
import com.example.bambergBeverageBox.base.MVCController;
import com.example.bambergBeverageBox.beverage.service.BeverageModelService;
import com.example.bambergBeverageBox.enums.Menu;
import jakarta.servlet.http.HttpServletRequest;
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
public class BeverageController extends MVCController {

    private final BeverageModelService beverageModelService;



    @GetMapping
    @TitleAndContent(title = "Beverage", content = "beverage/view", activeMenu = Menu.BEVERAGE_VIEW)
    public String view(
            Model model
    ) {

        log.debug("Rendering Product Page");
        return viewRoot;
    }

    @GetMapping(value = "/add")
    @TitleAndContent(title = "Add Beverage", content = "beverage/add", activeMenu = Menu.BEVERAGE_ADD)
    public String add(
            Model model
    ) {

        log.debug("Rendering Beverage Add Page");
        beverageModelService.addBeverageModel(model);
        return viewRoot;
    }

}
