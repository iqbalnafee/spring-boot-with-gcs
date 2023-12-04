package com.example.bambergBeverageBox.bottle.controller;

import com.example.bambergBeverageBox.annotations.TitleAndContent;
import com.example.bambergBeverageBox.base.MVCController;
import com.example.bambergBeverageBox.beverage.service.BeverageModelService;
import com.example.bambergBeverageBox.bottle.service.BottleModelService;
import com.example.bambergBeverageBox.enums.Menu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/bottle")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BottleController extends MVCController {

    private final BottleModelService bottleModelService;
    private final BeverageModelService beverageModelService;

    @GetMapping(value = "/add")
    @TitleAndContent(title = "Add Bottles", content = "bottle/add", activeMenu = Menu.BOTTLE_ADD)
    public String add(
            Model model
    ) {

        log.debug("Rendering Bottle Add Page");
        bottleModelService.addBottleModel(model);
        model.addAttribute("beverageList",
                beverageModelService.getBeverageDropdownList());
        return viewRoot;
    }

    @GetMapping(value = "/edit/{id}")
    @TitleAndContent(title = "Add Bottle", content = "bottle/add", activeMenu = Menu.BOTTLE_ADD)
    public String edit(
            Model model,
            @PathVariable("id") Long id) {

        log.debug("Rendering Bottle Edit Page");
        bottleModelService.editBeverageModel(model,id);
        return viewRoot;
    }
    @GetMapping(value = "/search")
    @TitleAndContent(title = "Add Bottle", content = "bottle/search", activeMenu = Menu.BOTTLE_ADD)
    public String search(
            Model model
    ) {

        log.debug("Rendering Bottle Search Page");
        return viewRoot;
    }
}
