package com.example.bambergBeverageBox.beverage.controller;

import com.example.bambergBeverageBox.annotations.TitleAndContent;
import com.example.bambergBeverageBox.base.MVCController;
import com.example.bambergBeverageBox.beverage.service.BeverageModelService;
import com.example.bambergBeverageBox.enums.Menu;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/beverage")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BeverageController extends MVCController {

    private final BeverageModelService beverageModelService;

    @GetMapping(value = "/add")
    @TitleAndContent(title = "Add Beverage", content = "beverage/add", activeMenu = Menu.BEVERAGE_ADD)
    public String add(
            Model model, final HttpSession session, Authentication authentication
    ) {

        log.debug("Rendering Beverage Add Page");
        beverageModelService.addBeverageModel(model);
        setTotalItemCountToCartFromSession(model,session, authentication);
        return viewRoot;
    }

    @GetMapping(value = "/edit/{id}")
    @TitleAndContent(title = "Add Beverage", content = "beverage/add", activeMenu = Menu.BEVERAGE_ADD)
    public String edit(
            Model model,
            @PathVariable("id") Long id) {

        log.debug("Rendering Beverage Edit Page");
        beverageModelService.editBeverageModel(model,id);
        return viewRoot;
    }
    @GetMapping(value = "/search")
    @TitleAndContent(title = "Add Beverage", content = "beverage/search", activeMenu = Menu.BEVERAGE_ADD)
    public String search(
            Model model
    ) {

        log.debug("Rendering Beverage Search Page");
        return viewRoot;
    }

}
