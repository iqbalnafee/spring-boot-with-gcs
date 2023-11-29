package com.example.bambergBeverageBox.util;

import com.example.bambergBeverageBox.enums.Menu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MvcUtil {

    @Value("${login.default-success-url:}")
    private String defaultSuccessUrl;

    public void addTitleAndContent(Model model, String title, String content, Menu activeMenu) {

        model.addAttribute("pageTitle", title);
        model.addAttribute("content", content);
        model.addAttribute("activeMenu", activeMenu);
        model.addAttribute("defaultSuccessUrl", defaultSuccessUrl);
    }

    public void addMenuPath(Model model, List<Menu> menuPath) {
        model.addAttribute("menuPath",menuPath);
    }
}
